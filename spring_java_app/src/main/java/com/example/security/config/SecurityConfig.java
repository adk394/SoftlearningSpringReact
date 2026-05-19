package com.example.security.config;

import com.example.security.jwt.JwtAuthenticationFilter;
import com.example.security.auth.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // endpoints publicos (login, registro y refresh-token)
                    http.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/refresh-token").permitAll();
                    
                    // get - todos los roles
                    http.requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/rest/orders/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    
                    // post - manager y admin
                    http.requestMatchers(HttpMethod.POST, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/clients/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/rest/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    
                    // put - manager y admin
                    http.requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/clients/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/rest/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    
                    // delete - solo admin
                    http.requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/clients/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/rest/orders/**").hasRole("ADMIN");
                    
                    // ============================================
                    // CONFIGURACIÓN JWT SIN VALIDACIÓN DE ROLES - ELECTRONICS
                    // ============================================
                    // Para los endpoints de /api/electronics/**, solo requerimos que el usuario
                    // esté autenticado (tenga un token JWT válido).
                    // NO verificamos roles específicos, por lo que cualquier usuario
                    // (USER, MANAGER o ADMIN) puede acceder a todos los métodos.
                    //
                    // .authenticated() = requiere token JWT válido, sin importar el rol
                    // Esto es diferente de hasAnyRole() o hasRole() que validan permisos específicos
                    //
                    // Endpoints protegidos:
                    // - GET    /api/electronics/**      -> Cualquier usuario autenticado
                    // - POST   /api/electronics/**      -> Cualquier usuario autenticado
                    // - PUT    /api/electronics/**      -> Cualquier usuario autenticado
                    // - DELETE /api/electronics/**      -> Cualquier usuario autenticado
                    http.requestMatchers("/api/electronics/**").authenticated();

                    // cualquier otra peticion requiere autenticacion
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
