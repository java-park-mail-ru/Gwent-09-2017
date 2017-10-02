package ru.mail.park.gwent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {
    private static final String ORIGIN = "https://gwentteam.herokuapp.com";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new MyWebMvcConfigurerAdapter();
    }

    private static class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/join").allowedOrigins(ORIGIN);
            registry.addMapping("/api/user").allowedOrigins(ORIGIN);
            registry.addMapping("/api/auth").allowedOrigins(ORIGIN);
        }
    }
}
