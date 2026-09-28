package com.scholartrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI scholarTrackOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ScholarTrack API")
                        .description("Scholarship application and eligibility tracker API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ScholarTrack Team")
                                .email("support@scholartrack.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
