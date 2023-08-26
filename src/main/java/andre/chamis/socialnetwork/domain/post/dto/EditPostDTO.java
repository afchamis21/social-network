package andre.chamis.socialnetwork.domain.post.dto;

/**
 * Data Transfer Object (DTO) for editing posts.
 */
public record EditPostDTO(
        Long postId,
        String content
) {
}
