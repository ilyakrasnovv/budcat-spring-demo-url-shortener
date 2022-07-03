package ru.ilkras.budcat.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotOnlyEnv {
    public final String baseUrl = System.getenv().get("BASE_URL"); // TODO init without env variables
    public final Boolean debugEndpointsEnabled = Boolean.parseBoolean(System.getenv().get("DEBUG_ENDPOINTS"));
    public final String databasePath = System.getenv().get("DATABASE_PATH");
    public final Long maxURLLength = Long.parseLong(System.getenv().get("MAX_URL_LENGTH"));

    public NotOnlyEnv() {
        Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);
        if (debugEndpointsEnabled) {
            logger.warn("Debug endpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
        logger.info(String.format("Maximum allowed URL length at database is %d", maxURLLength));
    }
}
