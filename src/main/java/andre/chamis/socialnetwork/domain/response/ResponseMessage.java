package andre.chamis.socialnetwork.domain.response;

import lombok.Data;

@Data
public class ResponseMessage<T> {
    private Metadata metadata;
    private T data;
}
