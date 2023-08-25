package andre.chamis.socialnetwork.domain.auth.repository;

import andre.chamis.socialnetwork.domain.auth.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenEntityJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    void deleteByToken(String token);

    boolean existsByToken(String token);

    int deleteAllByExpireDtBefore(Date now);

    void deleteAllByUsername(String username);
}
