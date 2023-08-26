package andre.chamis.socialnetwork.domain.response;

import lombok.Data;

import java.util.Set;

/**
 * Represents metadata associated with a response, typically containing messages.
 */
@Data
public class Metadata {
    /**
     * A set of messages included in the metadata.
     */
    private Set<String> messages;
}
