package com.bigcode.config;

/**
 * Pulls config values specifically from the environment, in this case, from the environment variables specified by
 * {@link #CLIENT_ID_ENVIRONMENT_VARIABLE_NAME} and {@link #CLIENT_SECRET_ENVIRONMENT_VARIABLE_NAME}, typically:
 *
 * BLIZZARD_CLIENT_ID
 * and
 * BLIZZARD_CLIENT_SECRET
 *
 * Other classes can import this service and read the values as required for their logic.
 */
public class EnvConfig {
    public static final String CLIENT_ID_ENVIRONMENT_VARIABLE_NAME = "BLIZZARD_CLIENT_ID";
    public static final String CLIENT_SECRET_ENVIRONMENT_VARIABLE_NAME = "BLIZZARD_CLIENT_SECRET";

    private static String clientId;
    private static String clientSecret;

    static {
        clientId = System.getenv(CLIENT_ID_ENVIRONMENT_VARIABLE_NAME);
        clientSecret = System.getenv(CLIENT_SECRET_ENVIRONMENT_VARIABLE_NAME);
    }

    public static String getClientId() {
        return clientId;
    }

    public static void setClientId(String clientId) {
        EnvConfig.clientId = clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static void setClientSecret(String clientSecret) {
        EnvConfig.clientSecret = clientSecret;
    }
}
