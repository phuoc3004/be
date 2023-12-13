package com.example.PBL6.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMcvConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Replace with your specific endpoint
                .allowedOrigins("http://localhost:3000") // Replace with the origin of your frontend application
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods, adjust as needed
                .allowCredentials(true); // Allow credentials like cookies, if required
    }
}
