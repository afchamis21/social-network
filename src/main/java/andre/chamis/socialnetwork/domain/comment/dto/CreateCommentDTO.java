package andre.chamis.socialnetwork.domain.comment.dto;

public record CreateCommentDTO(
        Long postId,
        String content
) {
}
