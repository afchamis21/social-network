package andre.chamis.socialnetwork.domain.post.dto;

public record CreatePostDTO(
        Long ownerId,
        String content
) {
}
