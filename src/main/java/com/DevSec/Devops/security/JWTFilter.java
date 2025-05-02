package com.DevSec.Devops.security;

import com.DevSec.Devops.model.User;
import com.DevSec.Devops.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public JWTFilter(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 🔥 Bypass for public endpoints and static resources
        String path = request.getRequestURI();
        if (
                path.startsWith("/api/auth") ||
                        path.startsWith("/login.html") ||
                        path.startsWith("/register.html") ||
                        path.startsWith("/index.html") ||
                        path.startsWith("/static/") ||
                        path.endsWith(".css") ||
                        path.endsWith(".js") ||
                        path.endsWith(".html") ||
                        path.endsWith(".png") ||
                        path.endsWith(".ico")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username).orElse(null);
                if (user != null && jwtUtil.validateToken(token, new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), new ArrayList<>()))) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}