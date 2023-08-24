package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyFriendsException extends ExceptionWithStatusCode{
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public UserAlreadyFriendsException(String message) {
        super(message, httpStatus);
    }
}
