package ru.mail.park.gwent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    private static final String ORIGIN = "https://gwent-front.herokuapp.com";
    private static final String LOCALHOST = "http://localhost:8000";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/join")
                .allowedOrigins(ORIGIN, LOCALHOST);

        registry.addMapping("/api/auth")
                .allowedOrigins(ORIGIN, LOCALHOST)
                .allowedMethods("GET", "POST", "DELETE");
    }
}
