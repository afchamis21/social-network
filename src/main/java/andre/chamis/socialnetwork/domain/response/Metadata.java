package andre.chamis.socialnetwork.domain.response;

import lombok.Data;

import java.util.Set;

@Data
public class Metadata {
    private Set<String> messages;
}
