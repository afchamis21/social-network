package andre.chamis.socialnetwork.domain.friend.relation.repository;

import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendRelationJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public Page<User> findPotentialFriends(Long currentUserId, Pageable pageable) {
        try {
            String query = """
                SELECT *, COUNT(user_id) OVER() AS total_results FROM users U
                WHERE U.user_id != :user_id
                  AND U.user_id NOT IN (SELECT user_id_1 FROM friends WHERE user_id_2 = :user_id)
                  AND U.user_id NOT IN (SELECT user_id_2 FROM friends WHERE user_id_1 = :user_id)
                  AND U.user_id NOT IN (SELECT sender FROM friend_requests WHERE receiver = :user_id)
                  AND U.user_id NOT IN (SELECT receiver FROM friend_requests WHERE sender = :user_id)
                ORDER BY U.create_dt
                LIMIT :limit OFFSET :offset;
                """;

            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageable.getPageSize());
            params.put("offset", pageable.getOffset());

            params.put("user_id", currentUserId);

            return jdbcTemplate.queryForObject(query, params, new PotentialFriendsRowMapper(pageable));
        } catch (EmptyResultDataAccessException ex) {
            return Page.empty();
        }
    }
}
