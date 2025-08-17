package com.codigopiojoso.forohub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("API ForoHub")
                        .description("API Rest de la aplicación ForoHub, que contiene las funcionalidades de un foro con creación de tópicos y respuestas, y un sistema de autenticación de usuarios y roles.")
                        .contact(new Contact()
                                .name("Augusto Paz")
                                .email("augustoignaciopaz@gmail.com"))
                        .version("1.0"));
    }
}