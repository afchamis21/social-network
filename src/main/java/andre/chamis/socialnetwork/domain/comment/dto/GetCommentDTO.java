package andre.chamis.socialnetwork.domain.comment.dto;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.Data;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for retrieving comment information.
 */
@Data
public class GetCommentDTO {
    /**
     * The ID of the comment.
     */
    private Long commentId;

    /**
     * The ID of the post to which the comment belongs.
     */
    private Long postId;

    /**
     * The content of the comment.
     */
    private String content;

    /**
     * The last update date of the comment.
     */
    private Date updateDt;

    /**
     * The owner user's information.
     */
    private GetUserDTO owner;

    /**
     * Fills in comment-related information from a Comment entity.
     *
     * @param comment The Comment entity to retrieve information from.
     * @return The updated GetCommentDTO instance.
     */
    public GetCommentDTO withComment(Comment comment){
        this.commentId = comment.getCommentId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.updateDt = comment.getUpdateDt();
        return this;
    }


    /**
     * Fills in user-related information from a User entity.
     *
     * @param user The User entity to retrieve owner information from.
     * @return The updated GetCommentDTO instance.
     */
    public GetCommentDTO withUser(User user){
        this.owner = GetUserDTO.fromUser(user);
        return this;
    }
}
