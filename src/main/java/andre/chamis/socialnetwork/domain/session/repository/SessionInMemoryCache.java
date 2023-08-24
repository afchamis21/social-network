package andre.chamis.socialnetwork.domain.session.repository;

import andre.chamis.socialnetwork.cache.InMemoryCache;
import andre.chamis.socialnetwork.domain.session.model.Session;
import org.springframework.stereotype.Repository;

@Repository
public class SessionInMemoryCache extends InMemoryCache<Long, Session> {
}
