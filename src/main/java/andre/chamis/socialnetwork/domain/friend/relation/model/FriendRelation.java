package andre.chamis.socialnetwork.domain.friend.relation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents a friend relation entity.
 */
@Data
@Entity
@Table(name = "friends")
public class FriendRelation {
    /**
     * Represents a friend relation entity.
     */
    @Id
    @GeneratedValue
    @Column(name = "friend_relation_id")
    private long friendRelationId;

    /**
     * The ID of the first user in the friend relation.
     */
    @Column(name = "user_id_1")
    private long userId1;

    /**
     * The ID of the second user in the friend relation.
     */
    @Column(name = "user_id_2")
    private long userId2;

    /**
     * The creation date of the friend relation.
     */
    @Column(name = "create_dt")
    private Date createDt;
}
