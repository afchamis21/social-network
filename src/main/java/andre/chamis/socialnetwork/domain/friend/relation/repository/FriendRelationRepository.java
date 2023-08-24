package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.friend.relation.model.FriendRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRelationRepository {
    private final FriendRelationJpaRepository friendRelationJpaRepository;

    public Page<FriendRelation> findUserFriends(Long userId, Pageable pageable){
        return friendRelationJpaRepository.findAllByUserId1(userId, pageable);
    }

    public void save(FriendRelation friendRelation) {
        friendRelationJpaRepository.save(friendRelation);
    }

    public boolean existsFriendRelationByUserIds(Long userId1, Long userId2) {
        return friendRelationJpaRepository.existsFriendRelationByUserIds(userId1, userId2);
    }

    public void deleteAllFriendRelationsByUserIds(Long userId1, Long userId2) {
        friendRelationJpaRepository.deleteAllFriendRelationsByUserIds(userId1, userId2);
    }
}
