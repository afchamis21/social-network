package andre.chamis.socialnetwork.domain.response;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.ExceptionWithStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Utility class for building response messages with metadata.
 */
public class ResponseMessageBuilder {

    /**
     * Builds a response entity with a body and HTTP status, including metadata.
     *
     * @param <T>        The type of the response body.
     * @param body       The response body.
     * @param httpStatus The HTTP status to include in the response.
     * @return A response entity containing the response message.
     */
    public static <T> ResponseEntity<ResponseMessage<T>> build(T body, HttpStatus httpStatus){
        Metadata metadata = buildMetadata();

        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setData(body);
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    /**
     * Builds a response entity with an empty body and an HTTP status, including metadata.
     *
     * @param httpStatus The HTTP status to include in the response.
     * @return A response entity containing the response message.
     */
    public static <Void> ResponseEntity<ResponseMessage<Void>> build(HttpStatus httpStatus){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    /**
     * Builds a response entity for an exception with an associated status, including metadata.
     *
     * @param exception The exception with an associated status.
     * @return A response entity containing the response message.
     */
    public static ResponseEntity<ResponseMessage<Void>> build(ExceptionWithStatusCode exception){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, exception.getHttpStatus());
    }

    /**
     * Builds a response entity for a generic exception, including metadata.
     *
     * @param exception The exception to be included in the response.
     * @return A response entity containing the response message.
     */
    public static ResponseEntity<ResponseMessage<Void>> build(Exception exception){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Builds the metadata for the response message.
     *
     * @return The metadata containing messages.
     */
    private static Metadata buildMetadata(){
        List<String> messages = ServiceContext.getContext().getMetadataMessages();
        Metadata metadata = new Metadata();
        metadata.setMessages(messages);

        return metadata;
    }
}
