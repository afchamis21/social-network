package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends ExceptionWithStatusCode{
    public InvalidDataException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public InvalidDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
