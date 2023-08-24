package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends ExceptionWithStatusCode{
    public SessionNotFoundException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public SessionNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
