package project.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.model.Permission;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Util class that provides methods for generation, validation, etc. of JWT token.
 */

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @Qualifier("jwtUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, List<String> roles, HttpServletRequest request) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", roles);
        claims.put("ip", request.getLocalAddr());
        claims.put("userAgent", request.getHeader("User-Agent"));
//        claims.put("permissions", getPermissionNames(role.getPermissions()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secret)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest httpServletRequest) {
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            Claims claims = jwsClaims.getBody();
            if (claims.get("ip") == null || (httpServletRequest.getLocalAddr() != null && !claims.get("ip").toString().equals(httpServletRequest.getLocalAddr())))
                return false;
            if (claims.get("userAgent") == null || (httpServletRequest.getHeader("User-Agent") != null && !claims.get("userAgent").toString().equals(httpServletRequest.getHeader("User-Agent"))))
                return false;
            return !claims.getExpiration().before(new Date());
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException | AuthenticationException ex) {
            System.out.println("Expired JWT token");
            httpServletRequest.setAttribute("expired", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            System.out.println("Jwt claims string is empty");
        }
        return false;
    }

    private List<String> getPermissionNames(List<Permission> permissions) {
        List<String> result = new ArrayList<>();
        permissions.forEach(permission -> result.add(permission.getName()));
        return result;
    }
}
