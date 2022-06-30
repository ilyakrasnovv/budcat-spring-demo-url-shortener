package ru.ilkras.budcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ilkras.budcat.data.NotOnlyEnv;

@SpringBootApplication
public class BudcatApplication {
    public static NotOnlyEnv NOE;

    public static void main(String[] args) {
        SpringApplication.run(BudcatApplication.class, args);
        NOE = new NotOnlyEnv();
    }

}
