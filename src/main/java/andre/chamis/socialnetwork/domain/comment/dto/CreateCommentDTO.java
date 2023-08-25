package andre.chamis.socialnetwork.domain.comment.dto;

public record CreateCommentDTO(
        Long postId,
        Long ownerId,
        String content
) {
}
