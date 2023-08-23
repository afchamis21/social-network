package andre.chamis.socialnetwork.domain.exceptions.dto;

import andre.chamis.socialnetwork.domain.exceptions.ExceptionWithStatusCode;

public record ExceptionDTO(
        String status,
        String message
) {
    public ExceptionDTO(ExceptionWithStatusCode exceptionWithStatusCode){
        this("error", exceptionWithStatusCode.getMessage());
    }
}
