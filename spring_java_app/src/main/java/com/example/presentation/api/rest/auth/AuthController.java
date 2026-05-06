package com.example.presentation.api.rest.auth;

import com.example.security.dtos.JwtResponse;
import com.example.security.dtos.LoginRequest;
import com.example.security.dtos.MessageResponse;
import com.example.security.dtos.RegisterRequest;
import com.example.security.entities.PermissionEntity;
import com.example.security.entities.RoleEntity;
import com.example.security.entities.UserEntity;
import com.example.security.jwt.JwtUtils;
import com.example.security.config.RoleEnum;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generar el token JWT
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        // Obtener los roles del usuario
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Buscar el ID del usuario
        UserEntity user = userRepository.findUserEntityByUsername(userDetails.getUsername()).orElse(null);

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user != null ? user.getId() : null,
                userDetails.getUsername(),
                roles
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: El usuario ya existe!"));
        }

        // Crear el nuevo usuario
        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        user.setAccountNoExpired(true);
        user.setAccountNoLocked(true);
        user.setCredentialNoExpired(true);

        // Asignar roles
        Set<String> strRoles = registerRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            // Por defecto asignar rol USER
            RoleEntity userRole = roleRepository.findByRoleEnum(RoleEnum.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toUpperCase()) {
                    case "ADMIN":
                        RoleEntity adminRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado."));
                        roles.add(adminRole);
                        break;
                    case "MANAGER":
                        RoleEntity managerRole = roleRepository.findByRoleEnum(RoleEnum.MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Rol MANAGER no encontrado."));
                        roles.add(managerRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByRoleEnum(RoleEnum.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente!"));
    }
}
