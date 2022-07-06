package ru.ilkras.budcat.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import java.io.IOException;
import java.nio.file.Files;

public class NotOnlyEnv {
    public final String baseUrl = System.getenv().get("BASE_URL"); // TODO init without env variables
    public final Boolean debugEndpointsEnabled = Boolean.parseBoolean(System.getenv().get("DEBUG_ENDPOINTS"));
    public final String databasePath = System.getenv().get("DATABASE_PATH");
    public final String formHtml = new String(
            Files.readAllBytes((new FileSystemResource(
            "src/main/java/ru/ilkras/budcat/resources/form.html").getFile()).toPath()));

    public NotOnlyEnv() throws IOException {
        Logger logger = LoggerFactory.getLogger(NotOnlyEnv.class);
        if (debugEndpointsEnabled) {
            logger.warn("Debug endpoints were enabled (env. DEBUG_ENDPOINTS)");
        }
        logger.info(String.format("RESPONDING AT %s", baseUrl));
        logger.info("src/main/java/ru/ilkras/budcat/resources/form.html has been loaded.");
    }
}
