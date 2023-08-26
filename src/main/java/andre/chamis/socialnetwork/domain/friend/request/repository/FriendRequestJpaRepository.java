package andre.chamis.socialnetwork.domain.friend.request.repository;

import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing friend request entities using JPA.
 */
@Repository
public interface FriendRequestJpaRepository extends JpaRepository<FriendRequest, Long> {

    /**
     * Retrieves a friend request between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return An Optional containing the found friend request, if any.
     */
    @Query("SELECT fr FROM FriendRequest fr WHERE (fr.sender = ?1 AND fr.receiver = ?2) OR (fr.sender = ?2 AND fr.receiver = ?1)")
    Optional<FriendRequest> findByUserIds(Long userId1, Long userId2);

    Page<FriendRequest> findAllByReceiver(Long receiverId, Pageable pageable);
}
