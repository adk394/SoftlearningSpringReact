package com.example;

import com.example.security.config.RoleEnum;
import com.example.security.entities.PermissionEntity;
import com.example.security.entities.RoleEntity;
import com.example.security.entities.UserEntity;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@Transactional
@Commit  // Esto evita el rollback y persiste los datos
public class LoadDefaultSecurityData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void setDefaultData() {
        // Este test carga los datos por defecto de seguridad en la base de datos
        
        // Verificar si ya existen los roles (para no duplicar)
        Optional<RoleEntity> existingAdminRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN);
        if (existingAdminRole.isPresent()) {
            System.out.println("Los datos de seguridad ya están cargados.");
            return;
        }
        
        /* Create PERMISSIONS */
        PermissionEntity createPermission = new PermissionEntity("CREATE");
        PermissionEntity readPermission = new PermissionEntity("READ");
        PermissionEntity updatePermission = new PermissionEntity("UPDATE");
        PermissionEntity deletePermission = new PermissionEntity("DELETE");

        /* Create ROLES */
        RoleEntity roleAdmin = new RoleEntity(RoleEnum.ADMIN, 
                                Set.of(createPermission, readPermission, updatePermission, deletePermission)
        );

        RoleEntity roleUser = new RoleEntity(RoleEnum.USER, 
                                Set.of(readPermission)
        );

        RoleEntity roleManager = new RoleEntity(RoleEnum.MANAGER, 
                                Set.of(createPermission, readPermission, updatePermission)
        );

        // Guardar roles (esto también guarda los permisos por CascadeType.ALL)
        List<RoleEntity> savedRoles = roleRepository.saveAll(List.of(roleAdmin, roleUser, roleManager));
        System.out.println("Roles creados: " + savedRoles.size());
        
        // Recuperar los roles guardados para asegurar que tienen ID
        RoleEntity savedAdminRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN).orElse(roleAdmin);
        RoleEntity savedUserRole = roleRepository.findByRoleEnum(RoleEnum.USER).orElse(roleUser);
        RoleEntity savedManagerRole = roleRepository.findByRoleEnum(RoleEnum.MANAGER).orElse(roleManager);

        /* CREATE USERS - usando los roles guardados */
        UserEntity admin = new UserEntity("admin", new BCryptPasswordEncoder().encode("1234"), true, 
                                true, true, true, Set.of(savedAdminRole)
        );

        UserEntity manager = new UserEntity("manager", new BCryptPasswordEncoder().encode("1234"), true, 
                                true, true, true, Set.of(savedManagerRole)
        );

        UserEntity user1 = new UserEntity("user1", new BCryptPasswordEncoder().encode("1234"), true, 
                                true, true, true, Set.of(savedUserRole)
        );

        UserEntity user2 = new UserEntity("user2", new BCryptPasswordEncoder().encode("1234"), true, 
                        true, true, true, Set.of(savedUserRole)
        );

        List<UserEntity> savedUsers = userRepository.saveAll(List.of(admin, manager, user1, user2));
        System.out.println("Usuarios creados: " + savedUsers.size());
        
        System.out.println("Datos de seguridad cargados exitosamente!");
        System.out.println("Usuarios creados:");
        System.out.println("  - admin / 1234 (ADMIN)");
        System.out.println("  - manager / 1234 (MANAGER)");
        System.out.println("  - user1 / 1234 (USER)");
        System.out.println("  - user2 / 1234 (USER)");
    }
}
