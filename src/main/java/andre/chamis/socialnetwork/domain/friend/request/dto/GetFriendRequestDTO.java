package andre.chamis.socialnetwork.domain.friend.request.dto;

import andre.chamis.socialnetwork.domain.friend.request.model.FriendRequest;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for retrieving friend request information.
 */
@Data
public final class GetFriendRequestDTO {
    /**
     * Data Transfer Object (DTO) for retrieving friend request information.
     */
    private Long requestId;

    /**
     * The sender's information.
     */
    private GetUserDTO sender;

    /**
     * The receiver's information.
     */
    private GetUserDTO receiver;

    /**
     * Fills in friend request-related information from a FriendRequest entity.
     *
     * @param friendRequest The FriendRequest entity to retrieve information from.
     * @return The updated GetFriendRequestDTO instance.
     */
    public GetFriendRequestDTO withFriendRequest(FriendRequest friendRequest){
        this.requestId = friendRequest.getFriendRequestId();
        return this;
    }

    /**
     * Fills in sender-related information from a User entity.
     *
     * @param user The User entity representing the sender.
     * @return The updated GetFriendRequestDTO instance.
     */
    public GetFriendRequestDTO withSender(User user){
        this.sender = GetUserDTO.fromUser(user);
        return this;
    }

    /**
     * Fills in receiver-related information from a User entity.
     *
     * @param user The User entity representing the receiver.
     * @return The updated GetFriendRequestDTO instance.
     */
    public GetFriendRequestDTO withReceiver(User user){
        this.receiver = GetUserDTO.fromUser(user);
        return this;
    }
}
