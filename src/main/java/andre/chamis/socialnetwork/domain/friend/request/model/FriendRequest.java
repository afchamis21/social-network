package andre.chamis.socialnetwork.domain.friend.request.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "friend_requests")
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long friendRequestId;

    private Long sender;

    private Long receiver;
}
