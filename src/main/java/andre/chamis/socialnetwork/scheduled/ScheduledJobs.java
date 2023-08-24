package andre.chamis.socialnetwork.scheduled;

import andre.chamis.socialnetwork.service.RefreshTokenService;
import andre.chamis.socialnetwork.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledJobs {
    private final RefreshTokenService refreshTokenService;
    private final SessionService sessionService;

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void deleteExpiredRefreshTokens(){
        int deletedTokens = refreshTokenService.deleteAllExpired();
        log.info("Deleted [{}] expired refresh tokens", deletedTokens);
    }

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void deleteExpiredSessions(){
        int deletedSessions = sessionService.deleteAllExpired();
        log.info("Deleted [{}] expired sessions", deletedSessions);
    }
}
