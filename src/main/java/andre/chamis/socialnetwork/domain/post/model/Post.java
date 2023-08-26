package andre.chamis.socialnetwork.domain.post.model;

import andre.chamis.socialnetwork.converter.StringBlobConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents a post entity in a social network.
 */
@Data
@Entity
@Table(name = "posts")
public class Post {
    /**
     * The unique identifier of the post.
     */
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    /**
     * The ID of the user who owns the post.
     */
    @Column(name = "owner_id")
    private Long ownerId;

    /**
     * The content of the post.
     */
    @Convert(converter = StringBlobConverter.class)
    private String content;

    /**
     * The creation date of the post.
     */
    @Column(name = "create_dt")
    private Date createDt;

    /**
     * The last update date of the post.
     */
    @Column(name = "update_dt")
    private Date updateDt;
}
