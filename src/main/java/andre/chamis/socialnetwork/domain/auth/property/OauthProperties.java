package andre.chamis.socialnetwork.domain.auth.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth.oauth")
public class OauthProperties {
    private ProviderProperties google;

    @Data
    public static class ProviderProperties {
        private String clientId;
        private String clientSecret;
    }
}
