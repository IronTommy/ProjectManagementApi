package com.example.projects.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // Указываем разрешённый домен
                .allowedOrigins("http://localhost:3000")  // Указываем разрешённый домен
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true)  // Разрешаем отправку куки
                .maxAge(3600);  // Это для кеширования CORS запросов
    }

}
