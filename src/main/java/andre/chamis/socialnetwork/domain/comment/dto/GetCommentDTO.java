package andre.chamis.socialnetwork.domain.comment.dto;

import andre.chamis.socialnetwork.domain.comment.model.Comment;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class GetCommentDTO {
    private Long commentId;
    private Long postId;
    private String content;
    private Date updateDt;
    private GetUserDTO owner;

    public GetCommentDTO withComment(Comment comment){
        this.commentId = comment.getCommentId();
        this.postId = comment.getPostId();
        this.content = comment.getContent();
        this.updateDt = comment.getUpdateDt();
        return this;
    }

    public GetCommentDTO withUser(User user){
        this.owner = GetUserDTO.fromUser(user);
        return this;
    }
}
