package com.example.security.config;

import com.example.security.entities.RoleEntity;
import com.example.security.entities.UserEntity;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("\n=== INICIALIZANDO DATOS ===");
        
        try {
            // paso 1: crear roles
            RoleEntity roleAdmin = createRoleIfNotExists(RoleEnum.ADMIN);
            RoleEntity roleUser = createRoleIfNotExists(RoleEnum.USER);
            RoleEntity roleManager = createRoleIfNotExists(RoleEnum.MANAGER);
            
            System.out.println("Roles creados: ADMIN, USER, MANAGER");

            // paso 2: crear usuarios
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            
            createUserIfNotExists("admin", encoder.encode("1234"), roleAdmin);
            createUserIfNotExists("manager", encoder.encode("1234"), roleManager);
            createUserIfNotExists("user1", encoder.encode("1234"), roleUser);
            createUserIfNotExists("user2", encoder.encode("1234"), roleUser);
            
            System.out.println("Usuarios creados: admin, manager, user1, user2");
            
            // verificacion
            verifyData();
            
            System.out.println("\n=== DATOS CARGADOS ===");
            System.out.println("Usuarios disponibles:");
            System.out.println("  admin   / 1234 (ADMIN)");
            System.out.println("  manager / 1234 (MANAGER)");
            System.out.println("  user1   / 1234 (USER)");
            System.out.println("  user2   / 1234 (USER)");
            System.out.println("======================\n");
            
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    private RoleEntity createRoleIfNotExists(RoleEnum roleEnum) {
        Optional<RoleEntity> existing = roleRepository.findByRoleEnum(roleEnum);
        if (existing.isPresent()) {
            return existing.get();
        }
        RoleEntity role = new RoleEntity(roleEnum);
        return roleRepository.save(role);
    }
    
    private UserEntity createUserIfNotExists(String username, String password, RoleEntity role) {
        Optional<UserEntity> existing = userRepository.findUserEntityByUsername(username);
        if (existing.isPresent()) {
            return existing.get();
        }
        UserEntity user = UserEntity.builder()
                .username(username)
                .password(password)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();
        user.getRoles().add(role);
        return userRepository.save(user);
    }
    
    private void verifyData() {
        long roleCount = roleRepository.count();
        long userCount = userRepository.count();
        
        System.out.println("Verificacion: Roles=" + roleCount + ", Usuarios=" + userCount);
    }
}
