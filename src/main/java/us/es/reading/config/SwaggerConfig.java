package us.es.reading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig { 
        
        @Value("${base.url:http://localhost:3000}")
        private String swaggerUrl;
        @Bean
        public OpenAPI customOpenAPI() {
                // Configurar Swagger para usar la base URL personalizada
                return new OpenAPI()
                                .info(new Info()
                                                .title("Reading Service API")
                                                .version("v1")
                                                .description("API Documentation for Reading Service"))
                                .addServersItem(new Server().url(swaggerUrl) // Aquí solo usamos apiVersion, no agregamos
                                                                     // "/readings" ni otras rutas adicionales
                                                .description("Base URL for Reading Service"));
        }

}