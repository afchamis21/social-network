package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception indicating that users are already friends.
 */
public class UserAlreadyFriendsException extends ExceptionWithStatusCode{
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    /**
     * Constructs a user already friends exception with the given message and HTTP status.
     *
     * @param message The exception message.
     */
    public UserAlreadyFriendsException(String message) {
        super(message, httpStatus);
    }
}
