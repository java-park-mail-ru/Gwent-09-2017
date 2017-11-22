package ru.mail.park.gwent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static ru.mail.park.gwent.consts.Constants.*;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(AUTH_URL)
                .allowedOrigins(ORIGIN, LOCALHOST);

        registry.addMapping(AUTH_URL)
                .allowedOrigins(ORIGIN, LOCALHOST)
                .allowedMethods("GET", "POST", "DELETE");
    }
}
