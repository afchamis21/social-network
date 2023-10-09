package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing friend relation operations.
 */
@Repository
@RequiredArgsConstructor
public class FriendRelationRepository {
    private final FriendRelationJpaRepository friendRelationJpaRepository;
    private final FriendRelationJdbcRepository friendRelationJdbcRepository;

    /**
     * Retrieves a page of friends for a specified user.
     *
     * @param userId   The ID of the user whose friends are being retrieved.
     * @param pageable The pagination information.
     * @return A page containing the user's friends.
     */
    public Page<FriendRelation> findUserFriends(Long userId, Pageable pageable){
        return friendRelationJpaRepository.findAllByUserId1(userId, pageable);
    }

    /**
     * Saves a friend relation.
     *
     * @param friendRelation The friend relation to be saved.
     */
    public void save(FriendRelation friendRelation) {
        friendRelationJpaRepository.save(friendRelation);
    }

    /**
     * Checks if a friend relation exists between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return True if a friend relation exists, false otherwise.
     */
    public boolean existsFriendRelationByUserIds(Long userId1, Long userId2) {
        return friendRelationJpaRepository.existsFriendRelationByUserIds(userId1, userId2);
    }

    /**
     * Deletes all friend relations between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     */
    public void deleteAllFriendRelationsByUserIds(Long userId1, Long userId2) {
        friendRelationJpaRepository.deleteAllFriendRelationsByUserIds(userId1, userId2);
    }

    public Page<User> findUserPotentialFriends(Long currentUserId, Pageable pageable) {
        return friendRelationJdbcRepository.findPotentialFriends(currentUserId, pageable);
    }
}
