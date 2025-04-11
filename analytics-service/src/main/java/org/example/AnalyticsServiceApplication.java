package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AnalyticsServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AnalyticsServiceApplication.class, args);
        Environment env = context.getEnvironment();
        String port = env.getProperty("server.port");
        System.out.println("🚀 Expense Service is running on port " + port);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
