package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.auth.dto.GoogleLoginDTO;
import andre.chamis.socialnetwork.domain.auth.dto.RefreshTokensDTO;
import andre.chamis.socialnetwork.domain.auth.dto.TokensDTO;
import andre.chamis.socialnetwork.domain.auth.property.OauthProperties;
import andre.chamis.socialnetwork.domain.exception.UnauthorizedException;
import andre.chamis.socialnetwork.domain.session.model.Session;
import andre.chamis.socialnetwork.domain.user.dto.CreateUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
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
    private final OauthProperties oauthProperties;

    /**
     * Authenticates a user and generates access and refresh tokens.
     *
     * @param loginDTO The DTO containing user login credentials.
     * @return DTO containing access and refresh tokens.
     */
    public TokensDTO authenticateUser(LoginDTO loginDTO) {
        Optional<User> userOptional = userService.validateUserCredential(loginDTO);
        User user = userOptional.orElseThrow(() -> new UnauthorizedException("Credenciais inválidas!"));

        return generateSessionAndTokens(user);
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

        return new TokensDTO(accessToken, refreshToken, (GetUserDTO) null);
    }

    /**
     * Logs out the current user by deleting refresh token and session.
     */
    public void logout() {
        User currentUser = userService.findCurrentUser();
        refreshTokenService.deleteTokenByUsername(currentUser.getUsername());
        sessionService.deleteCurrentSession();
    }

    /**
     * Handles Google login by verifying the Google ID token, creating or retrieving a user, and generating a JWT token.
     *
     * @param googleLoginDTO The GoogleLoginDTO containing the Google ID token.
     * @return JwtTokenDTO representing the JWT token response.
     * @throws UnauthorizedException if authentication fails or an error occurs during the process.
     */
    public TokensDTO authenticateGoogleUser(GoogleLoginDTO googleLoginDTO) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(oauthProperties.getGoogle().getClientId()))
                .build();

        try {
            // Verify the Google ID token.
            GoogleIdToken idToken = verifier.verify(googleLoginDTO.idToken());

            if (idToken == null) {
                throw new UnauthorizedException();
            }

            // Extract payload information from the ID token.
            IdToken.Payload payload = idToken.getPayload();

            Object payloadEmail = payload.get("email");

            if (payloadEmail == null) {
                throw new UnauthorizedException();
            }

            String email = (String) payloadEmail;

            // Find or create a user based on the email.
            Optional<User> userOptional = userService.findByEmail(email);

            User user;
            user = userOptional.orElseGet(() -> userService.createUser(new CreateUserDTO(
                    email,
                    email,
                    ""
            )));

            return generateSessionAndTokens(user);
        } catch (GeneralSecurityException | IOException e) {
            throw new UnauthorizedException();
        }
    }

    private TokensDTO generateSessionAndTokens(User user) {
        // Create a session for the user.
        Session session = sessionService.createSession(user);

        // Generate access and refresh token for the user.
        String accessToken = jwtService.createAccessToken(user, session);
        String refreshToken = jwtService.createRefreshToken(user, session);

        // Saves refresh token on database.
        refreshTokenService.saveTokenToDatabase(refreshToken);

        return new TokensDTO(accessToken, refreshToken, user);
    }
}
