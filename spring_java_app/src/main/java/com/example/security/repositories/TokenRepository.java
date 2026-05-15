package com.example.security.repositories;

import com.example.security.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = "SELECT t FROM TokenEntity t WHERE t.user.id = :id AND (t.isExpired = false OR t.isRevoked = false)")
    List<TokenEntity> findAllValidTokenByUser(Long id);

    Optional<TokenEntity> findByToken(String token);
}
