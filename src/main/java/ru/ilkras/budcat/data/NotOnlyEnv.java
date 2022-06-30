package ru.ilkras.budcat.data;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class NotOnlyEnv {
    public final String baseUrl = System.getenv().get("BASE_URL"); // TODO init without env variables
    public final Boolean debugEnpoitsEnabled = Boolean.parseBoolean(System.getenv().get("DEBUG_ENDPOINTS"));

    public NotOnlyEnv() {
        Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);
        if (debugEnpoitsEnabled) {
            logger.warn("Debug enpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
    }
}
