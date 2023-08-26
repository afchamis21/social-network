package andre.chamis.socialnetwork.domain.auth.dto;

/**
 * Data Transfer Object (DTO) for the refresh token used in token renewal.
 */
public record RefreshTokensDTO(
        String refreshToken
) {
}
