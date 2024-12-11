package us.es.reading.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Define los endpoints a los que se aplica CORS
                .allowedOrigins("http://localhost:3000") // Frontend en localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permitir cualquier cabecera
                .allowCredentials(true); // Permite las credenciales (si es necesario)
    }
}
