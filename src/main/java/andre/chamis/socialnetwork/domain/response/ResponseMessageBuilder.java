package andre.chamis.socialnetwork.domain.response;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.ExceptionWithStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public class ResponseMessageBuilder {
    public static <T> ResponseEntity<ResponseMessage<T>> build(T body, HttpStatus httpStatus){
        Metadata metadata = buildMetadata();

        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setData(body);
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    public static <Void> ResponseEntity<ResponseMessage<Void>> build(HttpStatus httpStatus){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, httpStatus);
    }

    public static ResponseEntity<ResponseMessage<Void>> build(ExceptionWithStatusCode exception){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, exception.getHttpStatus());
    }

    public static ResponseEntity<ResponseMessage<Void>> build(Exception exception){
        Metadata metadata = buildMetadata();

        ResponseMessage<Void> responseMessage = new ResponseMessage<>();
        responseMessage.setMetadata(metadata);

        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static Metadata buildMetadata(){
        Set<String> messages = ServiceContext.getContext().getMetadataMessages();
        Metadata metadata = new Metadata();
        metadata.setMessages(messages);

        return metadata;
    }
}
