package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ExceptionWithStatusCode{
    public EntityNotFoundException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public EntityNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
