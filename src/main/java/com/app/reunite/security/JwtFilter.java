package com.app.reunite.security;

import com.app.reunite.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if(authorization==null || !authorization.startsWith("Bearer ")){
            log.error("No token found");
            filterChain.doFilter(request,response);
        }
        assert authorization != null;
        String token = authorization.substring(7);

        boolean isTokenExpired = jwtService.isTokenExpired(token);
        boolean canBeTokenRenewed = jwtService.canBeTokenRenewed(token);
        if(isTokenExpired && !canBeTokenRenewed){
            log.error("Token expired");
            filterChain.doFilter(request,response);
        }
        String username = jwtService.getUsername(token);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            log.info("Authenticated user: {}",username);
            UserDetails userDetails = new UserDetails(username);
            if(isTokenExpired && canBeTokenRenewed){
                String renewedToken = jwtService.renewToken(token,userDetails);
                response.setHeader("Authorization","Bearer " + renewedToken);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        } else{
            log.error("Invalid Token or user already authenticated");
            filterChain.doFilter(request,response);
        }
    }
}
