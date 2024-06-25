package com.wikicoding.jwt_auth.security_config;

import com.wikicoding.jwt_auth.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Since we want the Jwt Filter to check all requests, we need to extend the class OncePerRequestFilter
     **/
    private final JwtService jwtService;
    /**
     * JwtService is a created class with the method extractUsername() also created by us
     **/
    private final UserDetailsService userDetailsService;
    /**
     * from spring security
     **/
    private final TokenRepository tokenRepository;
    /**
     * jwt filter logic below
     **/
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, /** this args should not be null so adding NotNull from springframework.lang **/
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/auth")) {
//            System.out.println("Jwt filter here!");
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); /** extracting the token, removing "Bearer " from the string authHeader **/
//        System.out.println("Provided token in the header at JwtFilter " + jwt);
        userEmail = jwtService.extractUsername(jwt); /** extracting userEmail from JWT token, it's like jwt.verify() in nodejs **/
//        System.out.println("Email extracted from Token at JwtFilter " + userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /** if userEmail !exists in the jwt and the user is not authenticated
             * means that the user is not logged in yet! **/
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response); /** passing to the next filter **/
    }
}
