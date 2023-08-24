package andre.chamis.socialnetwork.domain.auth.repository;

import andre.chamis.socialnetwork.domain.auth.model.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class RefreshTokenEntityRepository {
    private final RefreshTokenEntityJpaRepository jpaRepository;
    public RefreshTokenEntity save(RefreshTokenEntity refreshTokenEntity) {
        return jpaRepository.save(refreshTokenEntity);
    }

    public void deleteRefreshToken(String refreshToken){
        jpaRepository.deleteByToken(refreshToken);
    }

    public boolean existsByToken(String token) {
        return jpaRepository.existsByToken(token);
    }

    public int deleteAllExpired() {
        return jpaRepository.deleteAllByExpireDtBefore(Date.from(Instant.now()));
    }
}
