package andre.chamis.socialnetwork.domain.friend.request.repository;

import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FriendRequestRepository {
    private final FriendRequestJpaRepository friendRequestJpaRepository;

    public void createFriendRequest(FriendRequest friendRequest) {
        friendRequestJpaRepository.save(friendRequest);
    }

    public void deleteFriendRequest(long relationId) {
        friendRequestJpaRepository.deleteById(relationId);
    }

    public Optional<FriendRequest> findFriendRequestByUserIds(Long userId1, Long userId2) {
        return friendRequestJpaRepository.findByUserIds(userId1, userId2);
    }

    public Optional<FriendRequest> findFriendRequestByRequestId(Long requestId) {
        return friendRequestJpaRepository.findById(requestId);
    }
}
