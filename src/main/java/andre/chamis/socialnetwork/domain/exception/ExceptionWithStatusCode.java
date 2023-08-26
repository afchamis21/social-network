package andre.chamis.socialnetwork.domain.exception;

import andre.chamis.socialnetwork.context.ServiceContext;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ExceptionWithStatusCode extends RuntimeException {
    protected HttpStatus httpStatus;

    public ExceptionWithStatusCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ExceptionWithStatusCode(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        ServiceContext.addMessage(message);
    }
}
