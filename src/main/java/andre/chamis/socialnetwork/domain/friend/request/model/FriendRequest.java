package andre.chamis.socialnetwork.domain.friend.request.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a friend request entity.
 */
@Data
@Entity
@Table(name = "friend_requests")
public class FriendRequest {
    /**
     * Represents a friend request entity.
     */
    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long friendRequestId;

    /**
     * The ID of the user who sent the friend request.
     */
    private Long sender;

    /**
     * The ID of the user who received the friend request.
     */
    private Long receiver;
}
