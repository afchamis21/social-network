package andre.chamis.socialnetwork.domain.auth.dto;

/**
 * Data Transfer Object (DTO) for tokens used in authentication.
 */
public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
