package andre.chamis.socialnetwork.scheduled;

import andre.chamis.socialnetwork.domain.user.repository.UserRepository;
import andre.chamis.socialnetwork.service.PostService;
import andre.chamis.socialnetwork.service.RefreshTokenService;
import andre.chamis.socialnetwork.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledJobs {
    private final RefreshTokenService refreshTokenService;
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final PostService postService;

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void deleteExpiredRefreshTokens(){
        Instant executionStart = Instant.now();
        int deletedTokens = refreshTokenService.deleteAllExpired();
        Instant executionEnd = Instant.now();
        log.info(
                "Deleted [{} expired refresh tokens]. Execution took [{} ms]",
                deletedTokens,
                Duration.between(executionStart, executionEnd).toMillis()
        );
    }

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void deleteExpiredSessions(){
        Instant executionStart = Instant.now();
        int deletedSessions = sessionService.deleteAllExpired();
        Instant executionEnd = Instant.now();
        log.info(
                "Deleted [{} expired sessions]. Execution tool [{} ms]",
                deletedSessions,
                Duration.between(executionStart, executionEnd).toMillis()
        );
    }

    @Scheduled(cron = "0 0 0 * * *") // Every day at 00:00
    public void deleteAllPosts(){
        Instant executionStart = Instant.now();
        int deletedPosts = postService.deleteAllPosts();
        Instant executionEnd = Instant.now();
        log.info(
                "Deleted [{} posts]. Execution tool [{} ms]",
                deletedPosts,
                Duration.between(executionStart, executionEnd).toMillis()
        );
    }

    @Scheduled(cron = "0 0 */2 * * *")// Every two hours
    public void resetCache(){
        Instant executionStart = Instant.now();
        int cachedUsers = userRepository.initializeCache();
        Instant executionEnd = Instant.now();
        log.info(
                "Reset Cache with [{} users]. Execution took [{} ms]",
                cachedUsers,
                Duration.between(executionStart, executionEnd).toMillis()
        );
    }
}
