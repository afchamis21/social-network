package andre.chamis.socialnetwork.domain.auth.dto;

public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
