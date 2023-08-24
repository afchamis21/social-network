package andre.chamis.socialnetwork.interceptor;

import andre.chamis.socialnetwork.domain.auth.annotation.JwtAuthenticated;
import andre.chamis.socialnetwork.domain.auth.annotation.NonAuthenticated;
import andre.chamis.socialnetwork.domain.exceptions.UnauthorizedException;
import andre.chamis.socialnetwork.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)){
            return true; // Let fail for 404
        }

        AuthType authType = getRequestAuthType(handlerMethod);

        return switch (authType) {
            case JWT_TOKEN -> handleJwtAuthentication(request);
            case NON_AUTHENTICATED -> true;
        };
    }

    private boolean handleJwtAuthentication(HttpServletRequest request) {
        Optional<String> tokenFromHeaders = getTokenFromHeaders(request);

        String token = tokenFromHeaders.orElseThrow(UnauthorizedException::new);

        boolean isTokenValid = jwtService.validateAccessToken(token);
        if (!isTokenValid){
            throw new UnauthorizedException();
        }

        return true;
    }

    private Optional<String> getTokenFromHeaders(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isBlank()) {
            return Optional.empty();
        }
        String[] authHeaderArray = authHeader.split(" ");
        if (!authHeaderArray[0].equalsIgnoreCase("bearer")){
            return Optional.empty();
        }

        String token = authHeaderArray[1];
        return Optional.of(token);
    }

    private enum AuthType {
        JWT_TOKEN,
        NON_AUTHENTICATED
    }

    private AuthType getRequestAuthType(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethod().isAnnotationPresent(JwtAuthenticated.class)){
            return AuthType.JWT_TOKEN;
        }

        if (handlerMethod.getMethod().isAnnotationPresent(NonAuthenticated.class)){
            return AuthType.NON_AUTHENTICATED;
        }

        if (handlerMethod.getBeanType().isAnnotationPresent(JwtAuthenticated.class)){
            return AuthType.JWT_TOKEN;
        }

        if (handlerMethod.getBeanType().isAnnotationPresent(NonAuthenticated.class)){
            return AuthType.NON_AUTHENTICATED;
        }

        return AuthType.JWT_TOKEN;
    }
}
