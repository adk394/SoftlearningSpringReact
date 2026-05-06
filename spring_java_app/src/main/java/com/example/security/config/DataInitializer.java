package com.example.security.config;

import com.example.security.entities.PermissionEntity;
import com.example.security.entities.RoleEntity;
import com.example.security.entities.UserEntity;
import com.example.security.repositories.PermissionRepository;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("\n=========================================");
        System.out.println("INICIALIZANDO DATOS DE SEGURIDAD JWT");
        System.out.println("=========================================");
        
        try {
            // PASO 1: Crear PERMISOS
            System.out.println("\n>>> PASO 1/4: Creando permisos...");
            PermissionEntity createPermission = createPermissionIfNotExists("CREATE");
            PermissionEntity readPermission = createPermissionIfNotExists("READ");
            PermissionEntity updatePermission = createPermissionIfNotExists("UPDATE");
            PermissionEntity deletePermission = createPermissionIfNotExists("DELETE");
            
            System.out.println("✓ Permisos creados: CREATE, READ, UPDATE, DELETE");

            // PASO 2: Crear ROLES con sus permisos
            System.out.println("\n>>> PASO 2/4: Creando roles...");
            RoleEntity roleAdmin = createRoleWithPermissions(RoleEnum.ADMIN, 
                                    Set.of(createPermission, readPermission, updatePermission, deletePermission));

            RoleEntity roleUser = createRoleWithPermissions(RoleEnum.USER, 
                                    Set.of(readPermission));

            RoleEntity roleManager = createRoleWithPermissions(RoleEnum.MANAGER, 
                                    Set.of(createPermission, readPermission, updatePermission));
            
            System.out.println("✓ Roles creados: ADMIN, USER, MANAGER");

            // PASO 3: Crear USUARIOS
            System.out.println("\n>>> PASO 3/4: Creando usuarios...");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            
            createUserIfNotExists("admin", encoder.encode("1234"), roleAdmin);
            createUserIfNotExists("manager", encoder.encode("1234"), roleManager);
            createUserIfNotExists("user1", encoder.encode("1234"), roleUser);
            createUserIfNotExists("user2", encoder.encode("1234"), roleUser);
            
            System.out.println("✓ Usuarios creados: admin, manager, user1, user2");
            
            // PASO 4: Verificación
            System.out.println("\n>>> PASO 4/4: Verificando datos...");
            verifyData();
            
            // VERIFICACIÓN FINAL
            System.out.println("\n=========================================");
            System.out.println("✅ DATOS CARGADOS EXITOSAMENTE!");
            System.out.println("=========================================");
            System.out.println("Usuarios disponibles para login:");
            System.out.println("  👤 admin   / 1234 (ADMIN)");
            System.out.println("  👤 manager / 1234 (MANAGER)");
            System.out.println("  👤 user1   / 1234 (USER)");
            System.out.println("  👤 user2   / 1234 (USER)");
            System.out.println("=========================================\n");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERROR al cargar datos de seguridad:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    private PermissionEntity createPermissionIfNotExists(String name) {
        Optional<PermissionEntity> existing = permissionRepository.findByName(name);
        if (existing.isPresent()) {
            return existing.get();
        }
        PermissionEntity permission = new PermissionEntity(name);
        return permissionRepository.save(permission);
    }
    
    private RoleEntity createRoleWithPermissions(RoleEnum roleEnum, Set<PermissionEntity> permissions) {
        Optional<RoleEntity> existingRoleOpt = roleRepository.findByRoleEnum(roleEnum);
        
        if (existingRoleOpt.isPresent()) {
            RoleEntity existingRole = existingRoleOpt.get();
            // Verificar si tiene todos los permisos necesarios
            if (existingRole.getPermissionList().containsAll(permissions)) {
                return existingRole;
            }
            // Si no tiene todos los permisos, actualizarlos
            existingRole.setPermissionList(new HashSet<>(permissions));
            return roleRepository.save(existingRole);
        }
        
        // Crear nuevo rol
        RoleEntity role = new RoleEntity(roleEnum, new HashSet<>(permissions));
        return roleRepository.save(role);
    }
    
    private UserEntity createUserIfNotExists(String username, String password, RoleEntity role) {
        Optional<UserEntity> existing = userRepository.findUserEntityByUsername(username);
        if (existing.isPresent()) {
            return existing.get();
        }
        UserEntity user = new UserEntity(username, password, true, true, true, true, 
                                         new HashSet<>(Set.of(role)));
        return userRepository.save(user);
    }
    
    private void verifyData() {
        long permCount = permissionRepository.count();
        long roleCount = roleRepository.count();
        long userCount = userRepository.count();
        
        System.out.println("  - Permisos: " + permCount + "/4");
        System.out.println("  - Roles: " + roleCount + "/3");
        System.out.println("  - Usuarios: " + userCount + "/4");
        
        if (permCount != 4 || roleCount != 3 || userCount != 4) {
            System.err.println("⚠️  ADVERTENCIA: Algunos datos pueden estar incompletos!");
        } else {
            System.out.println("✓ Todos los datos verificados correctamente");
        }
    }
}
