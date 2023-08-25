package andre.chamis.socialnetwork.domain.post.dto;

import andre.chamis.socialnetwork.domain.post.model.Post;
import andre.chamis.socialnetwork.domain.user.dto.GetUserDTO;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GetPostDTO {
    private Long postId;
    private String content;
    private Date updateDt;
    private GetUserDTO owner;

    public GetPostDTO withPost(Post post){
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.updateDt = post.getUpdateDt();
        return this;
    }

    public GetPostDTO withUser(User user) {
        this.owner = GetUserDTO.fromUser(user);
        return this;
    }
}
