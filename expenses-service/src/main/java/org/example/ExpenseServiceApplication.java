package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExpenseServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExpenseServiceApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port");
            System.out.println("🚀 Expense Service is running on port " + port);
    }
}
