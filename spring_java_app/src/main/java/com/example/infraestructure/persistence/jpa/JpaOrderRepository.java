package com.example.infraestructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.core.entities.order.dtos.OrderDTO;

import jakarta.transaction.Transactional;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderDTO, Integer> {

    public Optional<OrderDTO> findById(int id);

    public List<OrderDTO> findByStatus(String status);

    @Query(value = "SELECT o FROM OrderDTO o WHERE o.status LIKE %:status%")
    public List<OrderDTO> findByPartialStatus(String status);

    @Query(value = "SELECT count(o) FROM OrderDTO o WHERE o.status LIKE %:status%")
    public Long countByPartialStatus(String status);

    @Transactional
    public OrderDTO save(OrderDTO order);

    public void deleteById(int id);
}
