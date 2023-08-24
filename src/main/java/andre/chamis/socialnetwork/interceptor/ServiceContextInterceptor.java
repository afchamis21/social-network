package andre.chamis.socialnetwork.interceptor;

import andre.chamis.socialnetwork.context.ServiceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class ServiceContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String executionId = request.getHeader(ServiceContext.EXECUTION_ID_KEY);
        ServiceContext context = ServiceContext.getContext(executionId);
        context.setStartTime(Instant.now());

        response.setHeader(ServiceContext.EXECUTION_ID_KEY, context.getExecutionId());

        log.info(
                "Starting Execution of [{} - {}] at [{}]",
                request.getMethod(),
                request.getRequestURI(),
                context.getStartTime()
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ServiceContext context = ServiceContext.getContext();
        context.setEndTime(Instant.now());
        Duration executionTime = Duration.between(context.getStartTime(), context.getEndTime());
        for (Exception exception: context.getExceptions()){
            log.error("An exception of type [{}] has occurred", exception.getClass().getSimpleName(), exception);
        }

        log.info(
                "Finishing execution of [{} - {}] at [{}]. Execution took [{}] ms",
                request.getMethod(),
                request.getRequestURI(),
                context.getEndTime(),
                executionTime.toMillis()
        );
    }
}
