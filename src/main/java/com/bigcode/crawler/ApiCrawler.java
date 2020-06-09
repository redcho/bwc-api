package com.bigcode.crawler;

import com.bigcode.auth.OAuth2FlowHandler;
import com.bigcode.auth.OAuth2FlowHandlerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static com.bigcode.config.AppConfig.*;

/**
 * Used to retrieve arbitrary data from the Blizzard Battle.net APIs, transform the result, and return the result.
 */
public class ApiCrawler {
    private OAuth2FlowHandler tokenManager = new OAuth2FlowHandlerImpl();
    private Logger log = LogManager.getLogger(ApiCrawler.class);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    /**
     * This is the default use case method.
     *
     * @param relativePath The relative URL against which to make the request
     * @param params A map of the values to be added to the URL as query parameters
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String getDataFromRelativePath(final String relativePath, final Map<String, String> params) throws IOException, URISyntaxException, InterruptedException {
        String token = tokenManager.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
//                .uri(URI.create(AppConfig.BASE_URL + relativePath))
                .uri(URI.create( BASE_URL + relativePath + "?namespace=" + NS_DYNAMIC + "&locale=" + LOCALE ))
                .setHeader("Authorization", "Bearer " + token)
//                .setHeader("region", "eu")
//                .setHeader("namespace", "dynamic-eu")
//                .setHeader("locale", "en_GB")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
//        System.out.println(response.statusCode());

        // print response body
        System.out.println("RESPONSE = " + response.body());

        return response.body();
    }

}
