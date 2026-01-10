package com.example.nexo.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.nexo.repository.UserRepository;
import com.example.nexo.service.TokenBlacklistService;
import com.example.nexo.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenBlacklistService tokenBlacklistService;
    
    public SecurityFilter(TokenService tokkenService, UserRepository userRepository, TokenBlacklistService tokenBlacklistService) {
        this.tokenService = tokkenService;
        this.userRepository = userRepository;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){
            if(tokenBlacklistService.isTokenBlackListed(token)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            var email = tokenService.validateToken(token);
            if(email != null && !email.isEmpty()){
                UserDetails user = userRepository.findByEmail(email);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        
        return authHeader.replace("Bearer ", "");
    }
}
