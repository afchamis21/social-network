package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.exceptions.ExceptionWithStatusCode;
import andre.chamis.socialnetwork.domain.exceptions.dto.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ExceptionWithStatusCode.class)
    public ResponseEntity<?> handeExceptionWithStatusCode(ExceptionWithStatusCode ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex);
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }
}
