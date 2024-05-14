package project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import project.security.jwt.JwtUser;

import java.util.Optional;

@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<JwtUser> {
    public Optional<JwtUser> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(JwtUser.class::cast);
    }
}
