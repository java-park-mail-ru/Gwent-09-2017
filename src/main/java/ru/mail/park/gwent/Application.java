package ru.mail.park.gwent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {
    private static final String ORIGIN = "https://gwentteam.herokuapp.com";
    private static final String LOCALHOST = "http://localhost:8000";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new MyWebMvcConfigurerAdapter();
    }

    private static class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/join").allowedOrigins(ORIGIN, LOCALHOST);
            registry.addMapping("/api/user").allowedOrigins(ORIGIN, LOCALHOST).allowedMethods("PUT");
            registry.addMapping("/api/auth").allowedOrigins(ORIGIN, LOCALHOST).allowedMethods("GET", "POST", "DELETE");
        }
    }
}
