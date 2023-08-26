package andre.chamis.socialnetwork.domain.post.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object (DAO) class for managing post-related database operations.
 */
@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Deletes a post and its associated comments by post ID and owner ID.
     *
     * @param postId  The ID of the post to be deleted.
     * @param ownerId The ID of the owner of the post.
     */
    public void deletePostById(Long postId, Long ownerId){
        String query = "DELETE FROM posts WHERE post_id = :postId AND owner_id = :ownerId";
        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);
        params.put("ownerId", ownerId);
        namedParameterJdbcTemplate.update(query, params);

        query = "DELETE FROM comments WHERE post_id = :postId";
        namedParameterJdbcTemplate.update(query, params);
    }
}
