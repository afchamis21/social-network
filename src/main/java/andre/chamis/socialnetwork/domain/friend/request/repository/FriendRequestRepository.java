package andre.chamis.socialnetwork.domain.friend.request.repository;

import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for managing friend request operations.
 */
@Repository
@RequiredArgsConstructor
public class FriendRequestRepository {
    private final FriendRequestJpaRepository friendRequestJpaRepository;

    /**
     * Creates a new friend request.
     *
     * @param friendRequest The friend request to be created.
     */
    public FriendRequest createFriendRequest(FriendRequest friendRequest) {
        return friendRequestJpaRepository.save(friendRequest);
    }

    /**
     * Deletes a friend request by its relation ID.
     *
     * @param friendRequestId The ID of the friend request to be deleted.
     */
    public void deleteFriendRequest(long friendRequestId) {
        friendRequestJpaRepository.deleteById(friendRequestId);
    }

    /**
     * Retrieves a friend request between two specified user IDs.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return An Optional containing the found friend request, if any.
     */
    public Optional<FriendRequest> findFriendRequestByUserIds(Long userId1, Long userId2) {
        return friendRequestJpaRepository.findByUserIds(userId1, userId2);
    }

    /**
     * Retrieves a friend request by its request ID.
     *
     * @param requestId The ID of the friend request to be retrieved.
     * @return An Optional containing the found friend request, if any.
     */
    public Optional<FriendRequest> findFriendRequestByRequestId(Long requestId) {
        return friendRequestJpaRepository.findById(requestId);
    }

    /**
     * Retrieves a page of friend requests received by a specified user.
     *
     * @param receiverId The ID of the user who received the friend requests.
     * @param pageable   The pagination information.
     * @return A page containing the friend requests received by the user.
     */
    public Page<FriendRequest> findByReceiverId(Long receiverId, Pageable pageable) {
        return friendRequestJpaRepository.findAllByReceiver(receiverId, pageable);
    }
}
