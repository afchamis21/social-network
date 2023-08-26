package andre.chamis.socialnetwork.domain.user.dto;

/**
 * Data Transfer Object (DTO) for updating user information.
 */
public record UpdateUserDTO(
        String username,
        String email,
        String password
) {
}
