package andre.chamis.socialnetwork.domain.session.repository;

import andre.chamis.socialnetwork.domain.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SessionJpaRepository extends JpaRepository<Session, Long> {
    List<Session> deleteAllByExpireDtBefore(Date expireDt);
}
