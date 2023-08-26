package andre.chamis.socialnetwork.domain.post.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
