package andre.chamis.socialnetwork.domain.auth.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private TokenConfig accessToken;
    private TokenConfig refreshToken;

    @Data
    public static class TokenConfig {
        private int duration;
        private ChronoUnit unit;
        private String encryptionKey;
    }
}
