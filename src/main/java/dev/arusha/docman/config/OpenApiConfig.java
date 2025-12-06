package dev.arusha.docman.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI documentManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Document Manager API")
                        .description("A service to manage documents and tags with advanced search capabilities.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sepehr Ghardashi")
                                .email("sepehr.ghardashi@gmail.com")));
    }
}