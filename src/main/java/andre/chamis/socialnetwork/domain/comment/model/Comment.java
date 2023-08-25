package andre.chamis.socialnetwork.domain.comment.model;

import andre.chamis.socialnetwork.converter.StringBlobConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long ownerId;

    @Convert(converter = StringBlobConverter.class)
    private String content;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "update_dt")
    private Date updateDt;
}
