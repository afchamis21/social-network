package andre.chamis.socialnetwork.domain.user.repository;

import andre.chamis.socialnetwork.cache.InMemoryCache;
import andre.chamis.socialnetwork.domain.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserInMemoryCache extends InMemoryCache<Long, User> {
    public Set<User> findAllWithMatchingUsername(String username){
        Map<Long, User> cache = super.getCache();

        return cache.values().stream().filter(
                (User user) -> user.getUsername().startsWith(username)
        ).collect(Collectors.toSet());
    }
}
