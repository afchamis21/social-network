package andre.chamis.socialnetwork.domain.post.dto;

import andre.chamis.socialnetwork.domain.post.model.Post;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for retrieving post information.
 */
@Data
@NoArgsConstructor
public class GetPostDTO {
    /**
     * The ID of the post.
     */
    private Long postId;

    /**
     * The content of the post.
     */
    private String content;

    /**
     * The last update date of the post.
     */
    private Date updateDt;

    /**
     * The owner user's information.
     */
    private GetUserDTO owner;

    /**
     * Fills in post-related information from a Post entity.
     *
     * @param post The Post entity to retrieve information from.
     * @return The updated GetPostDTO instance.
     */
    public GetPostDTO withPost(Post post){
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.updateDt = post.getUpdateDt();
        return this;
    }

    /**
     * Fills in user-related information from a User entity.
     *
     * @param user The User entity to retrieve owner information from.
     * @return The updated GetPostDTO instance.
     */
    public GetPostDTO withUser(User user) {
        this.owner = GetUserDTO.fromUser(user);
        return this;
    }
}
