package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.ExceptionWithStatusCode;
import andre.chamis.socialnetwork.domain.exception.dto.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ExceptionWithStatusCode.class)
    public ResponseEntity<?> handeExceptionWithStatusCode(ExceptionWithStatusCode ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex);
        ServiceContext.addException(ex);
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex);
        ServiceContext.addException(ex);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
