package andre.chamis.socialnetwork.domain.user.repository;

import andre.chamis.socialnetwork.cache.InMemoryCache;
import andre.chamis.socialnetwork.domain.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An {@link InMemoryCache} for caching user entities in memory.
 */
@Repository
public class UserInMemoryCache extends InMemoryCache<Long, User> {
    /**
     * Finds and returns a set of users with usernames that match the given prefix.
     *
     * @param username The username prefix to match against.
     * @return A set of users with matching usernames.
     */
    public Set<User> findAllWithMatchingUsername(String username){
        Map<Long, User> cache = super.getCache();

        return cache.values().stream().filter(
                (User user) -> user.getUsername().startsWith(username)
        ).collect(Collectors.toSet());
    }
}
