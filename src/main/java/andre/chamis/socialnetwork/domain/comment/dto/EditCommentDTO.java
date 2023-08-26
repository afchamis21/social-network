package andre.chamis.socialnetwork.domain.comment.dto;

/**
 * Data Transfer Object (DTO) for editing comments.
 */
public record EditCommentDTO(
        Long commentId,
        String content
) {
}
