package andre.chamis.socialnetwork.context;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Slf4j
public class ServiceContext {
    public static String EXECUTION_ID_KEY = "execution-id";

    private static ThreadLocal<ServiceContext> threadLocal = new ThreadLocal<>();

    private String executionId;

    private List<Exception> exceptions = new ArrayList<>();

    private Instant startTime;
    
    private Instant endTime;
    
    private String sessionId; // TODO: 24/08/2023 Implement session

    public synchronized static ServiceContext getContext() {
        return getContext(null);
    }

    public synchronized static ServiceContext getContext(String executionId) {
        ServiceContext context = threadLocal.get();
        if (context == null){
            return createNewContext(executionId);
        }

        return context;
    }

    private static ServiceContext createNewContext(String executionId) {
        ServiceContext context = new ServiceContext(executionId);
        threadLocal.set(context);

        return context;
    }

    private ServiceContext(String executionId) {
        executionId = executionId == null || executionId.isBlank()
                ? UUID.randomUUID().toString()
                : executionId;

        this.executionId = executionId;

        MDC.put(EXECUTION_ID_KEY, executionId);
    }

    public static void clearContext(){
        threadLocal.remove();
        MDC.remove(EXECUTION_ID_KEY);
    }

    public static void addException(Exception ex) {
        getContext().exceptions.add(ex);
    }
}
