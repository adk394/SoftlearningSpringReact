package com.example.security.services;

import com.example.security.dtos.LoginRequest;
import com.example.security.dtos.RegisterRequest;
import com.example.security.dtos.TokenResponse;
import com.example.security.entities.TokenEntity;
import com.example.security.entities.UserEntity;
import com.example.security.jwt.JwtUtils;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.TokenRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.config.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        // Crear usuario con roles por defecto
        Set<RoleEnum> roles = new HashSet<>();
        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            roles.add(RoleEnum.USER);
        } else {
            request.getRoles().forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(RoleEnum.ADMIN);
                    case "manager" -> roles.add(RoleEnum.MANAGER);
                    default -> roles.add(RoleEnum.USER);
                }
            });
        }

        // Crear el usuario usando Builder
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();

        // Asignar roles
        roles.forEach(roleEnum -> {
            roleRepository.findByRoleEnum(roleEnum)
                    .ifPresent(role -> user.getRoles().add(role));
        });

        UserEntity savedUser = userRepository.save(user);
        String jwtToken = jwtUtils.generateToken(savedUser);
        String refreshToken = jwtUtils.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Transactional
    public TokenResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        final UserEntity user = userRepository.findUserEntityByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found"));

        final String accessToken = jwtUtils.generateToken(user);
        final String refreshToken = jwtUtils.generateRefreshToken(user);
        
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse refreshToken(final String authentication) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtUtils.extractUsername(refreshToken);
        
        if (userEmail == null) {
            return null;
        }

        final UserEntity user = userRepository.findUserEntityByUsername(userEmail)
                .orElseThrow(() -> new RuntimeException("Error: User not found"));
                
        final boolean isTokenValid = jwtUtils.isTokenValid(refreshToken, user);
        
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtUtils.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        TokenEntity token = TokenEntity.builder()
                .token(jwtToken)
                .tokenType("BEARER")
                .isRevoked(false)
                .isExpired(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final UserEntity user) {
        final List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }
}
