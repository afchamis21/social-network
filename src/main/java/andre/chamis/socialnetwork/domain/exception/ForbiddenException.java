package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ExceptionWithStatusCode{
    private static final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    public ForbiddenException() {
        super(httpStatus);
    }

    public ForbiddenException(String message) {
        super(message, httpStatus);
    }
}
