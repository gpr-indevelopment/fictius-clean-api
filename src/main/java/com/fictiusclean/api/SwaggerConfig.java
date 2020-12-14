package com.fictiusclean.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

@Configuration
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("Gabriel Robaina", "https://github.com/gpr-indevelopment", "gpr.indevelopment@gmail.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Fictius Clean API", "Swagger documentation", "1.0", "", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Collections.singletonList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
