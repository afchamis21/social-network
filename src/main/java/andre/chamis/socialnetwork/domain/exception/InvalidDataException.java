package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception indicating invalid data . Such as missing params
 */
public class InvalidDataException extends ExceptionWithStatusCode {

    /**
     * Constructs an invalid data exception with the given HTTP status.
     *
     * @param httpStatus The HTTP status associated with the exception.
     */
    public InvalidDataException(HttpStatus httpStatus) {
        super(httpStatus);
    }

    /**
     * Constructs an invalid data exception with the given message and HTTP status.
     *
     * @param message    The exception message.
     * @param httpStatus The HTTP status associated with the exception.
     */
    public InvalidDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
