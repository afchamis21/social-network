package andre.chamis.socialnetwork.domain.user.dto;

public record UpdateUserDTO(
        String username,
        String email,
        String password
) {
}
