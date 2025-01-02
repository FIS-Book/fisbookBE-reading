package us.es.reading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
   
    @Value("${readings.base.url}/readings/api-docs")
    private String readingsBaseUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reading Service API")
                        .version("v1")
                        .description("API Documentation for Reading Service"))
                .addServersItem(new Server()
                        .url("")// Base URL vacío para evitar duplicaciones.
                        .description("Base URL for Reading Service"));
    }
}