package andre.chamis.socialnetwork.domain.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ExceptionWithStatusCode{
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    public UnauthorizedException() {
        super(httpStatus);
    }

    public UnauthorizedException(String message) {
        super(message, httpStatus);
    }
}
