package andre.chamis.socialnetwork.domain.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ExceptionWithStatusCode{
    public UserNotFoundException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
