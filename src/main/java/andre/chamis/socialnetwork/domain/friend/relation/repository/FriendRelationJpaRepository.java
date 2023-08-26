package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing friend relation entities using JPA.
 */
@Repository
public interface FriendRelationJpaRepository extends JpaRepository<FriendRelation, Long> {
    /**
     * Retrieves a page of friend relations involving the specified user ID.
     *
     * @param userId   The ID of the user involved in the friend relations.
     * @param pageable The pagination information.
     * @return A page containing the friend relations involving the user.
     */
    Page<FriendRelation> findAllByUserId1(Long userId, Pageable pageable);

    /**
     * Checks if a friend relation exists between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return True if a friend relation exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END FROM FriendRelation fr WHERE ((fr.userId1 = ?1 AND fr.userId2 = ?2) OR (fr.userId1 = ?2 AND fr.userId2 = ?1))")
    boolean existsFriendRelationByUserIds(Long userId1, Long userId2);

    /**
     * Deletes all friend relations between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     */
    @Query("DELETE FROM FriendRelation WHERE ((userId1 = ?1 AND userId2 = ?2) OR (userId1 = ?2 AND userId2 = ?1))")
    void deleteAllFriendRelationsByUserIds(Long userId1, Long userId2);
}
