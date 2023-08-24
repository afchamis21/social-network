package andre.chamis.socialnetwork.domain.friend.request.repository;

import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestJpaRepository extends JpaRepository<FriendRequest, Long> {
    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender = ?1 AND fr.receiver = ?2) OR (fr.sender = ?2 AND fr.receiver = ?1)")
    Optional<FriendRequest> findByUserIds(Long userId1, Long userId2);
}
