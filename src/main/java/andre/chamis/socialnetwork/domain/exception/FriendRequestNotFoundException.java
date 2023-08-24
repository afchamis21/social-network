package andre.chamis.socialnetwork.domain.exception;

import org.springframework.http.HttpStatus;

public class FriendRequestNotFoundException extends ExceptionWithStatusCode{
    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public FriendRequestNotFoundException(Long requestId) {
        super("Solicitação de amizade não encontrada com o id " + requestId, httpStatus);
    }
}
