package ru.ilkras.budcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ilkras.budcat.data.NotOnlyEnv;

import java.io.IOException;

@SpringBootApplication
public class BudcatApplication {
    public static NotOnlyEnv NOE;

    public static void main(String[] args) throws IOException {
        NOE = new NotOnlyEnv();
        SpringApplication.run(BudcatApplication.class, args);
    }

}
