package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.ExceptionWithStatusCode;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ExceptionWithStatusCode.class)
    public ResponseEntity<ResponseMessage<Void>> handeExceptionWithStatusCode(ExceptionWithStatusCode ex){
        ServiceContext.addException(ex);
        return ResponseMessageBuilder.build(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage<Void>> handleGenericException(Exception ex){
        ServiceContext.addException(ex);
        return ResponseMessageBuilder.build(ex);
    }
}
