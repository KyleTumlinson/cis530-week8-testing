package edu.bellevue.cis530.week8.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Services API - Bellevue University")
                        .description("OpenAPI documentation for the CIS 530 Student Services API")
                        .version("v1.0.0")
                        .contact(new Contact().name("CIS 530").email("sazizian@bellevue.edu"))
                        .license(new License().name("Apache 2.0").url("https://www.apache" +
                                ".org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Springdoc reference")
                        .url("https:/springdoc.org"));
    }
}
