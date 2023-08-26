package andre.chamis.socialnetwork.domain.comment.dto;

/**
 * Data Transfer Object (DTO) for creating comments.
 */
public record CreateCommentDTO(
        Long postId,
        String content
) {
}
