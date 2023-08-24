package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.auth.property.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${spring.application.name}")
    private String appName;
    private final JwtProperties jwtProperties;

    private Algorithm accessTokenAlgorithm;
    private Algorithm refreshTokenAlgorithm;

    @PostConstruct
    void buildAlgorithms(){
        accessTokenAlgorithm = Algorithm.HMAC256(jwtProperties.getAccessToken().getEncryptionKey().getBytes());
        refreshTokenAlgorithm = Algorithm.HMAC256(jwtProperties.getRefreshToken().getEncryptionKey().getBytes());
    }

    public String createAccessToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer(appName)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(
                        jwtProperties.getAccessToken().getDuration(),
                        jwtProperties.getAccessToken().getUnit()
                )).sign(accessTokenAlgorithm);
    }

    public String createRefreshToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer(appName)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(
                        jwtProperties.getRefreshToken().getDuration(),
                        jwtProperties.getRefreshToken().getUnit()
                )).sign(refreshTokenAlgorithm);
    }

    public boolean validateAccessToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(accessTokenAlgorithm).withIssuer(appName).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(refreshTokenAlgorithm).withIssuer(appName).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public Date getTokenExpiresAt(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt();
    }

    public Date getTokenIssuedAt(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getIssuedAt();
    }

    public String getTokenSubject(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
}
