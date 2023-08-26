package andre.chamis.socialnetwork.domain.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents a user in the system.
 */
@Data
@Entity
@Table(name = "users")
public class User {
    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    /**
     * The unique username of the user.
     */
    @Column(unique = true)
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The creation date of the user's record.
     */
    @Column(name = "create_dt")
    private Date createDt;

    /**
     * The update date of the user's record.
     */
    @Column(name = "update_dt")
    private Date updateDt;
}
