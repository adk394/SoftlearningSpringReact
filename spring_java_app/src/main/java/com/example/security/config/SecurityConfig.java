package com.example.security.config;

import com.example.security.jwt.JwtAuthenticationFilter;
import com.example.security.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@EnableMethodSecurity(prePostEnabled = true) // activa @PreAuthorize, @PostAuthorize, etc.
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Deshabilitar CSRF (no es necesario con JWT)
                .csrf(csrf -> csrf.disable())
                // Sin sesiones (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configurar rutas
                .authorizeHttpRequests(http -> {
                    // Endpoints públicos (autenticación)
                    http.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll();
                    
                    // Endpoints GET - Accesible por USER, MANAGER, ADMIN
                    http.requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    
                    // Endpoints POST - Solo MANAGER y ADMIN
                    http.requestMatchers(HttpMethod.POST, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/clients/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    
                    // Endpoints PUT - Solo MANAGER y ADMIN
                    http.requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/clients/**").hasAnyRole("MANAGER", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/orders/**").hasAnyRole("MANAGER", "ADMIN");
                    
                    // Endpoints DELETE - Solo ADMIN
                    http.requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/clients/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasRole("ADMIN");

                    // Cualquier otra solicitud requiere autenticación
                    http.anyRequest().authenticated();
                })
                // Agregar el filtro JWT antes del filtro de autenticación de username/password
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Configurar proveedor de autenticación
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
