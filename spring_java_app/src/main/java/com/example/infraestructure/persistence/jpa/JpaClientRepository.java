package com.example.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.core.entities.client.dtos.ClientDTO;
import jakarta.transaction.Transactional;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientDTO, Integer> {
    Optional<ClientDTO> findById(int id);

    List<ClientDTO> findByNamePerson(String namePerson);

    @Query("SELECT c FROM ClientDTO c WHERE c.namePerson LIKE %:namePerson%")
    List<ClientDTO> findByPartialNamePerson(String namePerson);

    @Query("SELECT count(c) FROM ClientDTO c WHERE c.namePerson LIKE %:namePerson%")
    Long countByPartialNamePerson(String namePerson);

    @Transactional
    ClientDTO save(ClientDTO client);

    void deleteById(int id);
}