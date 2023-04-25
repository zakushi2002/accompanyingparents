package com.webapp.accompanyingparents.config.security.jwt;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.Permission;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationHeader(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = getAccessToken(request);
        if (!jwtTokenUtil.validateAccessToken(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        setAuthenticationContext(accessToken, request);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Lấy thông tin token
     */
    private UserDetails getUserDetails(String accessToken) {
        Account userDetails = new Account();
        Claims claims = jwtTokenUtil.parseClaims(accessToken);

        String claimRoles = (String) claims.get("role");
        // claimRoles = claimRoles.replace("[","").replace("]","").replaceAll("Role\\(id=","").replaceAll(", name=","").replaceAll("\\)","");
        // System.out.println(claimRoles);
        claimRoles = claimRoles.replace("[", "").replace("]", "");
        // System.out.println(claimRoles);
        String[] roleNames = claimRoles.split(",");

        String claimPermissions = (String) claims.get("permissions");
        claimPermissions = claimPermissions.replace("[", "").replace("]", "");
        // System.out.println(claimPermissions);
        String[] permissionNames = claimPermissions.split(" ,");

        // System.out.println(roleNames[0].trim());
        Role role = new Role(roleNames[0].trim());
        for (String aPermissionName : permissionNames) {
            // System.out.println(aPermissionName.trim());
            role.addPermission(new Permission(aPermissionName.trim().toUpperCase()));
        }
        userDetails.addRole(role);

        String subject = (String) claims.get(Claims.SUBJECT);
        String[] subjectArray = subject.split(","); // jwtTokenUtil.getSubject(accessToken).split(",");
        userDetails.setId(Long.parseLong(subjectArray[0]));
        userDetails.setEmail(subjectArray[1]);
        return userDetails;
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        // System.out.println(header);
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim(); // Lấy token loại bỏ "Bearer"
        System.out.println("Token: " + token);
        return token;
    }
}