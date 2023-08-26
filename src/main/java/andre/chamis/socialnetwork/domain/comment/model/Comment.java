package andre.chamis.socialnetwork.domain.comment.model;

import andre.chamis.socialnetwork.converter.StringBlobConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Represents a comment entity associated with a post.
 */
@Data
@Entity
@Table(name = "comments")
public class Comment {
    /**
     * The unique identifier of the comment.
     */
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * The ID of the post to which the comment belongs.
     */
    @Column(name = "post_id")
    private Long postId;

    /**
     * The ID of the owner of the comment.
     */
    @Column(name = "user_id")
    private Long ownerId;

    /**
     * The content of the comment.
     */
    @Convert(converter = StringBlobConverter.class)
    private String content;

    /**
     * The creation date of the comment.
     */
    @Column(name = "create_dt")
    private Date createDt;

    /**
     * The last update date of the comment.
     */
    @Column(name = "update_dt")
    private Date updateDt;
}
