package us.es.reading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${base.url:http://localhost:3000}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") 
        .allowedOrigins(allowedOrigins) 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") 
                .allowedHeaders("*") 
                .allowCredentials(true); 
    }
}
