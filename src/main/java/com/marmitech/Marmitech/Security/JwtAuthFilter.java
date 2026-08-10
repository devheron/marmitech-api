package com.marmitech.Marmitech.Security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwUtil jwUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwUtil.isTokenValid(token)) {
                String email = jwUtil.getEmailFromToken(token);
                String cargo = jwUtil.getCargoFromToken(token);

                List<SimpleGrantedAuthority> authorities = cargo == null
                        ? List.of()
                        : List.of(new SimpleGrantedAuthority("ROLE_" + cargo.toUpperCase()));

                var authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Suporte para tokens JWT do Firebase Auth
                try {
                    String[] parts = token.split("\\.");
                    if (parts.length >= 2) {
                        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                        JsonNode jsonNode = objectMapper.readTree(payloadJson);

                        String email = jsonNode.has("email") ? jsonNode.get("email").asText()
                                : (jsonNode.has("sub") ? jsonNode.get("sub").asText() : null);

                        String cargo = null;
                        if (jsonNode.has("cargo")) {
                            cargo = jsonNode.get("cargo").asText();
                        } else if (jsonNode.has("role")) {
                            cargo = jsonNode.get("role").asText();
                        }

                        if (email != null) {
                            List<SimpleGrantedAuthority> authorities = cargo == null || cargo.isBlank()
                                    ? List.of()
                                    : List.of(new SimpleGrantedAuthority("ROLE_" + cargo.toUpperCase()));

                            var authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}