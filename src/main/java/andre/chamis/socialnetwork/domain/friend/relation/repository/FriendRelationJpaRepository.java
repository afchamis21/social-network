package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRelationJpaRepository extends JpaRepository<FriendRelation, Long> {
    Page<FriendRelation> findAllByUserId1(Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END FROM FriendRelation fr WHERE ((fr.userId1 = ?1 AND fr.userId2 = ?2) OR (fr.userId1 = ?2 AND fr.userId2 = ?1))")
    boolean existsFriendRelationByUserIds(Long userId1, Long userId2);

    @Query("DELETE FROM FriendRelation WHERE ((userId1 = ?1 AND userId2 = ?2) OR (userId1 = ?2 AND userId2 = ?1))")
    void deleteAllFriendRelationsByUserIds(Long userId1, Long userId2);
}
