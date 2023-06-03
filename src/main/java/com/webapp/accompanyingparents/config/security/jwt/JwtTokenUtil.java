package com.webapp.accompanyingparents.config.security.jwt;

import com.webapp.accompanyingparents.model.Account;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {
    /**
     * Set thời gian sống cho Token
     */
    private static final long EXPIRE_DURATION = 31L * 24 * 60 * 60 * 1000; // 31 days
    @Value("${app.jwt.secret}")
    private String secretKey;

    /**
     * Tạo token
     */
    public String generateAccessToken(Account account) {
        Collection<Object> listPermission = new ArrayList<>();
        /*for (Permission permission: account.getRole().getAuthorities()) {
            listPermission.addAll(role.getAuthorities());
        }*/
        //System.out.println(listPermission);
        return Jwts.builder()
                .setSubject(account.getId() + ", " + account.getUsername())
                .claim("role", account.getAuthorities().toString())
                .claim("permissions", account.getRole().getAuthorities().toString())
                .setIssuer("ToanNguyen")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Validate Token
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expired", ex);
        } catch (IllegalArgumentException ex) {
            log.error("Token is null, empty or has only whitespace", ex);
        } catch (MalformedJwtException ex) {
            log.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            log.error("Signature validation failed", ex);
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}