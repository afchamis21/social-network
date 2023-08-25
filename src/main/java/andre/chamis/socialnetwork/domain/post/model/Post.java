package andre.chamis.socialnetwork.domain.post.model;

import andre.chamis.socialnetwork.converter.StringBlobConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Convert(converter = StringBlobConverter.class)
    private String content;

    @Column(name = "create_dt")
    private Date createDt;

    @Column(name = "update_dt")
    private Date updateDt;
}
