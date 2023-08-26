package andre.chamis.socialnetwork.controller;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
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

@RestController
@NonAuthenticated
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthorizationService authorizationService;
    @PostMapping("login")
    public ResponseEntity<ResponseMessage<TokensDTO>> login(@RequestBody LoginDTO loginDTO)  {
        TokensDTO tokensDTO = authorizationService.authenticateUser(loginDTO);
        return ResponseMessageBuilder.build(tokensDTO, HttpStatus.CREATED);
    }

    @PostMapping("refresh")
    public ResponseEntity<ResponseMessage<TokensDTO>> refresh(@RequestBody RefreshTokensDTO refreshTokensDTO)  {
        TokensDTO tokensDTO = authorizationService.refreshTokens(refreshTokensDTO);
        return ResponseMessageBuilder.build(tokensDTO, HttpStatus.OK);
    }

    @JwtAuthenticated
    @PostMapping("logout")
    public ResponseEntity<ResponseMessage<Void>> logout(){
        authorizationService.logout();
        return ResponseMessageBuilder.build(HttpStatus.OK);
    }
}
