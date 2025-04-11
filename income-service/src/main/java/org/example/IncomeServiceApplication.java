package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IncomeServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IncomeServiceApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        System.out.println("🚀 Income Service is running on port " + port);
    }
}
