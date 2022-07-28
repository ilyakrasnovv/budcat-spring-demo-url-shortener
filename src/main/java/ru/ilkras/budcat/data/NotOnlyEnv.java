package ru.ilkras.budcat.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotOnlyEnv {
    private String baseUrl = "0.0.0.0:8080";
    private Boolean debugEndpointsEnabled = false;
    private String databasePath = "jdbc:h2:./budcat_database";

    private final Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);

    public NotOnlyEnv() {
        try {
            baseUrl = loadEnv("BASE_URL");
            logSuccess("BASE_URL");
        } catch (NullPointerException ignored) {
        }
        try {
            debugEndpointsEnabled = Boolean.parseBoolean(loadEnv("DEBUG_ENDPOINTS"));
            logSuccess("DEBUG_ENDPOINTS", "debugEndpointsEnabled");
        } catch (NullPointerException ignored) {
        }
        try {
            databasePath = loadEnv("DATABASE_PATH");
            logSuccess("DATABASE_PATH");
        } catch (NullPointerException ignored) {
        }

        logger.info("Finished loading environmental variables.");

        if (debugEndpointsEnabled) {
            logger.warn("Debug endpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
    }

    void logSuccess(String envName) {
        logger.info("environmental variable " + envName + " was loaded successfully");
    }

    void logSuccess(String envName, String codeName) {
        logger.info("environmental variable " + envName + " was loaded successfully into " + codeName);
    }

    void logFailure(String envName, Throwable err) {
        logger.error("environmental variable " + envName + " was not defined. Initiated with default value.", err);
    }

    String loadEnv(String name) throws NullPointerException {
        String res = System.getenv().get(name);
        if (res == null) {
            NullPointerException error = new NullPointerException();
            logFailure(name, error);
            throw error;
        }
        return res;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Boolean getDebugEndpointsEnabled() {
        return debugEndpointsEnabled;
    }

    public String getDatabasePath() {
        return databasePath;
    }
}
