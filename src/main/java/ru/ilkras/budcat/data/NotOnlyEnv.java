package ru.ilkras.budcat.data;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Files;

public class NotOnlyEnv {
    String baseUrl = "0.0.0.0:8080";
    Boolean debugEndpointsEnabled = false;
    private String databasePath = "jdbc:h2:./budcat_database";

    private Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);

    void logSuccess(String envName, String codeName) {
        logger.info("environmental variable " + envName + " was loaded successfully into " + codeName);
    }

    void logFailure(String envName, Throwable err) {
        logger.error("environmental variable " + envName + " was not defined. Initiated with default value.", err);
    }

    String loadEnv(String name) throws NullPointerException {
        String res = System.getenv().get(name);
        if (res == null)
            throw new NullPointerException();
        return res;
    }

    public NotOnlyEnv() {
        try {
            baseUrl = loadEnv("BASE_URL");
            logSuccess("BASE_URL", "baseUrl");
        } catch (NullPointerException e) {
            logFailure("BASE_URL", e);
        }
        try {
            debugEndpointsEnabled = Boolean.parseBoolean(loadEnv("DEBUG_ENDPOINTS"));
            logSuccess("DEBUG_ENDPOINTS", "debugEndpointsEnabled");
        } catch (NullPointerException e) {
            logFailure("DEBUG_ENDPOINTS", e);
        }
        try {
            databasePath = loadEnv("DATABASE_PATH");
            logSuccess("DATABASE_PATH", "databasePath");
        } catch (NullPointerException e) {
            logFailure("DATABASE_PATH", e);
        }

        logger.info("Finished loading environmental variables.");

        if (debugEndpointsEnabled) {
            logger.warn("Debug endpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
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
