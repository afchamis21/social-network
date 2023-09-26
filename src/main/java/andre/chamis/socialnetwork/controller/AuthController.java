package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.auth.dto.GoogleLoginDTO;
import andre.chamis.socialnetwork.domain.auth.dto.RefreshTokensDTO;
import andre.chamis.socialnetwork.domain.auth.dto.TokensDTO;
import andre.chamis.socialnetwork.domain.response.ResponseMessage;
import andre.chamis.socialnetwork.domain.response.ResponseMessageBuilder;
import andre.chamis.socialnetwork.domain.user.dto.LoginDTO;
import andre.chamis.socialnetwork.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling user authentication and authorization.
 */
@RestController
@NonAuthenticated
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;

    /**
     * Authenticate a user and generate access and refresh tokens.
     *
     * @param loginDTO The DTO containing user credentials.
     * @return ResponseEntity containing the generated tokens and a status code.
     */
    @PostMapping("login")
    public ResponseEntity<ResponseMessage<TokensDTO>> login(@RequestBody LoginDTO loginDTO)  {
        TokensDTO tokensDTO = authorizationService.authenticateUser(loginDTO);
        return ResponseMessageBuilder.build(tokensDTO, HttpStatus.CREATED);
    }

    /**
     * Handles Google login by receiving a GoogleLoginDTO as the request body and returning a JWT token response.
     *
     * @param googleLoginDTO The GoogleLoginDTO containing the Google ID token.
     * @return ResponseEntity with a Response containing a JwtTokenDTO, representing the JWT token response.
     */
    @PostMapping("login/google")
    public ResponseEntity<ResponseMessage<TokensDTO>> googleLogin(@RequestBody GoogleLoginDTO googleLoginDTO)  {
        TokensDTO tokensDTO = authorizationService.authenticateGoogleUser(googleLoginDTO);
        return ResponseMessageBuilder.build(tokensDTO, HttpStatus.CREATED);
    }

    /**
     * Refresh access and refresh tokens using a valid refresh token.
     *
     * @param refreshTokensDTO The DTO containing the refresh token.
     * @return ResponseEntity containing the refreshed tokens and a status code.
     */
    @PostMapping("refresh")
    public ResponseEntity<ResponseMessage<TokensDTO>> refresh(@RequestBody RefreshTokensDTO refreshTokensDTO)  {
        TokensDTO tokensDTO = authorizationService.refreshTokens(refreshTokensDTO);
        return ResponseMessageBuilder.build(tokensDTO, HttpStatus.OK);
    }

    /**
     * Logout a user by invalidating their refresh token and session.
     *
     * @return ResponseEntity with a success message and status code.
     */
    @JwtAuthenticated
    @PostMapping("logout")
    public ResponseEntity<ResponseMessage<Void>> logout(){
        authorizationService.logout();
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }
}
