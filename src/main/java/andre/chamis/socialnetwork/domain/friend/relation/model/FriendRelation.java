package andre.chamis.socialnetwork.domain.friend.relation.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "friends")
public class FriendRelation {
    @Id
    @GeneratedValue
    @Column(name = "friend_relation_id")
    private long friendRelationId;

    @Column(name = "user_id_1")
    private long userId1;

    @Column(name = "user_id_2")
    private long userId2;
}
