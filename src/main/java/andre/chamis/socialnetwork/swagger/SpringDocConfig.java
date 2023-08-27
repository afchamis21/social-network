package andre.chamis.socialnetwork.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class for SpringDoc OpenAPI documentation generation.
 * This configuration is active when the profile is not set to 'prod'.
 */
@Profile("!prod")
@Configuration
public class SpringDocConfig {

    /**
     * Creates and configures the OpenAPI documentation for the API.
     *
     * @return An instance of OpenAPI containing API information and security settings.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(
                new Components().addSecuritySchemes(
                        "jwt-token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                )
        ).info(
                new Info()
                        .title("Andre Chamis Social Network")
                        .version("1.0.0")
        );
    }
}
