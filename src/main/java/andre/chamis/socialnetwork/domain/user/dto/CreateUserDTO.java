package andre.chamis.socialnetwork.domain.user.dto;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
