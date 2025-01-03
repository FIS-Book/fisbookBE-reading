package us.es.reading.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

        @Value("${base.url:http://localhost:3000}")
        private String swaggerUrl;     

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Reading Service API")
                                                .version("v1")
                                                .description("API Documentation for Reading Service"))
                                .addServersItem(new Server().url(swaggerUrl)
                                                .description("Base URL for Reading Service"))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")) // Especificamos que usaremos
                                                                                      // Bearer Token de tipo JWT
                                )
                                .security(Arrays.asList(new SecurityRequirement().addList("bearerAuth")));
        }

}