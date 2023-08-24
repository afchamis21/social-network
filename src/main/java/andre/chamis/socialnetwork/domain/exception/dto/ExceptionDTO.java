package andre.chamis.socialnetwork.domain.exception.dto;

public record ExceptionDTO(
        String status,
        String message
) {
    public ExceptionDTO(Exception exception){
        this("error", exception.getMessage());
    }
}
