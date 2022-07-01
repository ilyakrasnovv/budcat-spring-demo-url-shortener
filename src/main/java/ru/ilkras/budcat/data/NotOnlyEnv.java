package ru.ilkras.budcat.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotOnlyEnv {
    public final String baseUrl = System.getenv().get("BASE_URL"); // TODO init without env variables
    public final Boolean debugEndpointsEnabled = Boolean.parseBoolean(System.getenv().get("DEBUG_ENDPOINTS"));

    public NotOnlyEnv() {
        Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);
        if (debugEndpointsEnabled) {
            logger.warn("Debug endpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
    }
}
