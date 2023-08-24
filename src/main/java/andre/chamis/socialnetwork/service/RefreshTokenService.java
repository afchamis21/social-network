package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.domain.auth.model.RefreshTokenEntity;
import andre.chamis.socialnetwork.domain.auth.repository.RefreshTokenEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtService jwtService;
    private final RefreshTokenEntityRepository refreshTokenEntityRepository;

    public void saveTokenToDatabase(String refreshToken){
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUsername(jwtService.getTokenSubject(refreshToken));
        refreshTokenEntity.setCreateDt(jwtService.getTokenIssuedAt(refreshToken));
        refreshTokenEntity.setExpireDt(jwtService.getTokenExpiresAt(refreshToken));

        refreshTokenEntityRepository.save(refreshTokenEntity);
    }

    public boolean existsOnDatabase(String token){
        return refreshTokenEntityRepository.existsByToken(token);
    }

    public void deleteAllExpired(){

    }

    public void deleteToken(){

    }
}
