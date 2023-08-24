package andre.chamis.socialnetwork.domain.exceptions.dto;

public record ExceptionDTO(
        String status,
        String message
) {
    public ExceptionDTO(Exception exception){
        this("error", exception.getMessage());
    }
}
