package andre.chamis.socialnetwork.domain.user.repository;

import andre.chamis.socialnetwork.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
}
