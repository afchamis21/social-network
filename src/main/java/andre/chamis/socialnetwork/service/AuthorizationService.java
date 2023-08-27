package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.auth.dto.RefreshTokensDTO;
import andre.chamis.socialnetwork.domain.auth.dto.TokensDTO;
import andre.chamis.socialnetwork.domain.exception.UnauthorizedException;
import andre.chamis.socialnetwork.domain.session.model.Session;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * Service class for user authentication, token management, and logout.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final SessionService sessionService;

    /**
     * Authenticates a user and generates access and refresh tokens.
     *
     * @param loginDTO The DTO containing user login credentials.
     * @return DTO containing access and refresh tokens.
     */
    public TokensDTO authenticateUser(LoginDTO loginDTO) {
        Optional<User> userOptional = userService.validateUserCredential(loginDTO);
        User user = userOptional.orElseThrow(() -> new UnauthorizedException("Credenciais inválidas!"));

        Session session = sessionService.createSession(user);

        String accessToken = jwtService.createAccessToken(loginDTO.username(), session);
        String refreshToken = jwtService.createRefreshToken(loginDTO.username(), session);

        refreshTokenService.saveTokenToDatabase(refreshToken);

        return new TokensDTO(accessToken, refreshToken);
    }

    /**
     * Refreshes access and refresh tokens.
     *
     * @param refreshTokensDTO The DTO containing the refresh token.
     * @return DTO containing new access and refresh tokens.
     */
    public TokensDTO refreshTokens(RefreshTokensDTO refreshTokensDTO) {
        String refreshToken = refreshTokensDTO.refreshToken();
        boolean isTokenValid = jwtService.validateRefreshToken(refreshToken);
        if (!isTokenValid) {
            refreshTokenService.deleteToken(refreshToken);
            throw new UnauthorizedException("Token inválido");
        }

        boolean isTokenOnDatabase = refreshTokenService.existsOnDatabase(refreshToken);
        if (!isTokenOnDatabase) {
            throw new UnauthorizedException("Token inválido");
        }
        String username = jwtService.getTokenSubject(refreshToken);

        Long sessionId = jwtService.getSessionIdFromToken(refreshToken);
        Optional<Session> sessionOptional = sessionService.findSessionById(sessionId);
        Session session = sessionOptional.orElseThrow(() -> new UnauthorizedException("Sua sessão expirou! Faça login novamente"));

        boolean isSessionValid = sessionService.validateSessionIsNotExpired(session);
        if (!isSessionValid){
            throw new UnauthorizedException("Sua sessão expirou! Faça login novamente");
        }
        
        String accessToken = jwtService.createAccessToken(username, session);

        Date refreshTokenExpirationDate = jwtService.getTokenExpiresAt(refreshToken);
        Duration durationUntilRefreshTokenExpires = Duration.between(
                Instant.now(),
                refreshTokenExpirationDate.toInstant()
        );

        if (durationUntilRefreshTokenExpires.toHours() <= 2) {
            refreshToken = jwtService.createRefreshToken(username, session);
        }

        return new TokensDTO(accessToken, refreshToken);
    }

    /**
     * Logs out the current user by deleting refresh token and session.
     */
    public void logout() {
        User currentUser = userService.findCurrentUser();
        refreshTokenService.deleteTokenByUsername(currentUser.getUsername());
        sessionService.deleteCurrentSession();
    }
}
