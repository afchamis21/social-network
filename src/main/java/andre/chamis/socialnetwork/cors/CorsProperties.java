package andre.chamis.socialnetwork.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("auth.cors")
public class CorsProperties {
    private String[] allowedUris;
}
