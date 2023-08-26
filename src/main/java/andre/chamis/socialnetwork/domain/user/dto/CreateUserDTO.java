package andre.chamis.socialnetwork.domain.user.dto;

/**
 * Data Transfer Object (DTO) for creating a new user.
 */
public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
