package com.spacek.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Theta Spring Boot", description = "Theta Framework in Spring Boot", version = "v1"))
public class SwaggerConfig {
    private static final String AUTHORIZATION = "Authorization";

    // Access locally: http://localhost:9090/theta/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(AUTHORIZATION);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(AUTHORIZATION, securityScheme))
                .addSecurityItem(securityRequirement);
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}
