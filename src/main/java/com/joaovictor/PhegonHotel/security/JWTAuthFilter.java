package com.joaovictor.PhegonHotel.security;

import com.joaovictor.PhegonHotel.service.CustomUserDetailsService;
import com.joaovictor.PhegonHotel.util.JWTutils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTutils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {// verifica se o token é valido
        final String authHeader = request.getHeader("Authorization");// pega o token
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);// se não tiver token, continua
            return;
        }

        jwtToken = authHeader.substring(7);// pega o token
        userEmail = jwtUtils.extractUsername(jwtToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {// se o email for valido e não tiver autenticação
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (jwtUtils.isvalidateToken(jwtToken, userDetails)) {// se o token for valido
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();// cria um contexto de segurança
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());// cria um token
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// adiciona os detalhes
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);// seta o contexto de segurança
            }
        }
        filterChain.doFilter(request, response);// continua
    }
}
