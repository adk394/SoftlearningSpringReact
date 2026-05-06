package com.example.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Obtener el token del request
        final String token = getTokenFromRequest(request);

        // 2. Si no hay token, continuar con el siguiente filtro
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el username del token
        final String username = jwtUtils.extractUsername(token);

        // 4. Si hay usuario y no está autenticado en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 5. Cargar el usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 6. Validar el token
            if (jwtUtils.isTokenValid(token, userDetails)) {
                // 7. Crear el objeto de autenticación
                UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                
                // 8. Agregar detalles de la request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 9. Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 10. Continuar con el filtro
        filterChain.doFilter(request, response);
    }

    // Obtener el token del header Authorization
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        
        return null;
    }
}
