package com.adm.test.filter;


import com.adm.test.utility.exception.TokenException;
import com.adm.test.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(JwtFilter.class);
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    public JwtFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(BEARER)) {
            LOGGER.warn("No JWT with bearer type , authorization : {}", authorizationHeader);  // todo please change log
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.substring(BEARER.length());
        final String userName = jwtUtil.extractUserName(token);

        if (Objects.isNull(userName)) {
            LOGGER.warn("Invalid JWT : Could not extract username from it"); // todo please change log
            filterChain.doFilter(request, response);
            return;
        }

        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            LOGGER.warn("User already authenticated");  // todo please change log
            filterChain.doFilter(request, response);
            return;
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            jwtUtil.validateToken(token, userDetails.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (TokenException iat) {
            LOGGER.warn(iat.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            LOGGER.warn(e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
