package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends ExceptionWithStatusCode{
    public InvalidUserDataException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public InvalidUserDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
