package andre.chamis.socialnetwork.service;

import andre.chamis.socialnetwork.context.ServiceContext;
import andre.chamis.socialnetwork.domain.exception.EntityNotFoundException;
import andre.chamis.socialnetwork.domain.session.model.Session;
import andre.chamis.socialnetwork.domain.session.property.SessionProperties;
import andre.chamis.socialnetwork.domain.session.repository.SessionRepository;
import andre.chamis.socialnetwork.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionProperties sessionProperties;
    public Session createSession(User user) {
        Session session = new Session();
        session.setUserId(user.getUserId());
        session.setCreateDt(Date.from(Instant.now()));
        session.setExpireDt(Date.from(Instant.now().plus(
                sessionProperties.getDuration(), sessionProperties.getUnit()
        )));
        return sessionRepository.save(session);
    }

    public int deleteAllExpired(){
        return sessionRepository.deleteAllExpired();
    }

    public Optional<Session> findSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }
    
    public Long getCurrentUserId(){
        Long sessionId = ServiceContext.getContext().getSessionId();
        Optional<Session> sessionOptional = findSessionById(sessionId);
        Session session = sessionOptional.orElseThrow(() -> new EntityNotFoundException(HttpStatus.FORBIDDEN));
        return session.getUserId();
    }

    public boolean validateSessionIsNotExpired(Session session) {
        return session.getExpireDt().after(Date.from(Instant.now()));
    }
}
