package ru.robotbot.parking_reservation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить для всех путей
                .allowedOrigins("http://localhost:63342") // Разрешить все домены
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешить все методы
                .allowedHeaders("*") // Разрешить все заголовки
                .allowCredentials(true);
    }
}
