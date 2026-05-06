package com.example.security.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.security.entities.RoleEntity;
import com.example.security.config.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}
