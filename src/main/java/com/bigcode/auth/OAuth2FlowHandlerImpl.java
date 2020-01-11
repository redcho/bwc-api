package com.bigcode.auth;

import com.bigcode.config.AppConfig;
import com.bigcode.config.EnvConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Base64;

/**
 * {@inheritDoc}
 */
public class OAuth2FlowHandlerImpl implements OAuth2FlowHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    private String token = null;
    private Instant tokenExpiry = null; // Instant when the token will expire

    private final Logger log = LogManager.getLogger(OAuth2FlowHandlerImpl.class);
    private final Object tokenLock = new Object();
    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken() throws IOException {
        if(isTokenInvalid()){
            log.warn("---");
            log.warn("Fetching/Creating token.");

            String encodedCredentials = Base64.getEncoder().encodeToString(String.format("%s:%s", EnvConfig.getClientId(), EnvConfig.getClientSecret()).getBytes(AppConfig.ENCODING));

            // ------------------------------------------------- Allows testing/mocking of the URL connection object
            HttpURLConnection con = null;

            try{
            URL url = new URL(AppConfig.TOKEN_URL);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
            con.setDoOutput(true);
            con.getOutputStream().write("grant_type=client_credentials".getBytes(AppConfig.ENCODING));

            int responseCode = con.getResponseCode();
            log.warn(String.format("Sent 'POST' to %s requesting access token via client credentials grant type.", url));
            log.warn(String.format("Result code: %s", responseCode));

            String response = IOUtils.toString(con.getInputStream(),AppConfig.ENCODING);

            log.warn(String.format("Response: %s", response));

            // Reads the JSON response and converts it to TokenResponse class or throws an exception
            TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
            synchronized (tokenLock) {
                tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpires_in());
                token = tokenResponse.getAccess_token();
            }

            log.warn("---");
            } finally {
                if(con != null){
                    con.disconnect();
                }
            }
        }
        synchronized (tokenLock){
            return token;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTokenInvalid(){
        synchronized (tokenLock) {
            if (token == null) {
                return true;
            }
            if (tokenExpiry == null) {
                return true;
            }
            return Instant.now().isAfter(tokenExpiry);
        }
    }
}
