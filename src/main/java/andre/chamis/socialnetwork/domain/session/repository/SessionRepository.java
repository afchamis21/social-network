package andre.chamis.socialnetwork.domain.session.repository;

import andre.chamis.socialnetwork.domain.session.model.Session;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final SessionInMemoryCache inMemoryCache;
    private final SessionJpaRepository jpaRepository;

    @PostConstruct
    void initializeCache(){
        inMemoryCache.initializeCache(jpaRepository.findAll(), Session::getSessionId);
    }

    public Session save(Session session){
        session = jpaRepository.save(session);
        inMemoryCache.put(session.getSessionId(), session);

        return session;
    }

    public Optional<Session> findById(Long sessionId){
        Optional<Session> sessionOptionalFromCache = inMemoryCache.get(sessionId);
        if (sessionOptionalFromCache.isPresent()){
            return sessionOptionalFromCache;
        }

        Optional<Session> sessionOptionalFromDatabase = jpaRepository.findById(sessionId);
        if (sessionOptionalFromDatabase.isPresent()){
            Session session = sessionOptionalFromDatabase.get();
            inMemoryCache.put(session.getSessionId(), session);
        }

        return sessionOptionalFromDatabase;
    }

    public int deleteAllExpired(){
        List<Session> deletedSessions = jpaRepository.deleteAllByExpireDtBefore(Date.from(Instant.now()));
        inMemoryCache.deleteFromList(deletedSessions, Session::getSessionId);
        return deletedSessions.size();
    }
}
