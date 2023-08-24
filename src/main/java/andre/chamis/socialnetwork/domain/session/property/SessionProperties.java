package andre.chamis.socialnetwork.domain.session.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth.session")
public class SessionProperties {
    private int duration;
    private ChronoUnit unit;
}
