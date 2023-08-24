package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.auth.dto.RefreshTokensDTO;
import andre.chamis.socialnetwork.domain.auth.dto.TokensDTO;
import andre.chamis.socialnetwork.domain.exceptions.UnauthorizedException;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public TokensDTO authenticateUser(LoginDTO loginDTO) {
        boolean isUserCredentialsValid = userService.validateUserCredential(loginDTO);
        if (!isUserCredentialsValid){
            throw new UnauthorizedException();
        }

        // TODO: 24/08/2023 Create session

        String accessToken = jwtService.createAccessToken(loginDTO.username());
        String refreshToken = jwtService.createRefreshToken(loginDTO.username());

        refreshTokenService.saveTokenToDatabase(refreshToken);

        return new TokensDTO(accessToken, refreshToken);
    }

    public TokensDTO refreshTokens(RefreshTokensDTO refreshTokensDTO) {
        String refreshToken = refreshTokensDTO.refreshToken();
        boolean isTokenValid = jwtService.validateRefreshToken(refreshToken);
        if (!isTokenValid) {
            throw new UnauthorizedException();
        }

        boolean isTokenOnDatabase = refreshTokenService.existsOnDatabase(refreshToken);
        if (!isTokenOnDatabase) {
            throw new UnauthorizedException();
        }

        // TODO: 24/08/2023 Create session

        String username = jwtService.getTokenSubject(refreshToken);
        String accessToken = jwtService.createAccessToken(username);

        Date refreshTokenExpirationDate = jwtService.getTokenExpiresAt(refreshToken);
        Duration durationUntilRefreshTokenExpires = Duration.between(
                Instant.now(),
                refreshTokenExpirationDate.toInstant()
        );

        if (durationUntilRefreshTokenExpires.toHours() <= 2) {
            refreshToken = jwtService.createRefreshToken(username);
        }

        return new TokensDTO(accessToken, refreshToken);
    }
}
