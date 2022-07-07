package ru.ilkras.budcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ilkras.budcat.data.NotOnlyEnv;

import java.io.IOException;

@SpringBootApplication
public class BudcatApplication {
    public static NotOnlyEnv NOE;

    public BudcatApplication() throws IOException {
        NOE = new NotOnlyEnv();
    }

    public static void main(String[] args) {
        SpringApplication.run(BudcatApplication.class, args);
    }

}
