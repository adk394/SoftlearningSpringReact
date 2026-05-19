package com.example.core.entities.electronics.persistence;

import com.example.core.entities.electronics.dtos.ElectronicsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ElectronicsRepository - Acceso a datos para Electronics
 * Extiende JpaRepository para operaciones CRUD basicas
 * Spring genera implementacion automatica
 */
@Repository
public interface ElectronicsRepository extends JpaRepository<ElectronicsDTO, Integer> {

    // Buscar por codigo de producto (idProduct)
    Optional<ElectronicsDTO> findByIdProduct(String idProduct);

    // Buscar por marca
    List<ElectronicsDTO> findByBrand(String brand);

    // Buscar por disponibilidad
    List<ElectronicsDTO> findByIsAvailable(boolean isAvailable);

    // Busqueda parcial por nombre (LIKE %text%)
    List<ElectronicsDTO> findByNameContaining(String name);

    // Buscar con garantia mayor a X meses
    List<ElectronicsDTO> findByWarrantyMonthsGreaterThan(int months);

    // Buscar por rango de precio
    List<ElectronicsDTO> findByPriceBetween(double minPrice, double maxPrice);

    // Contar productos por marca
    long countByBrand(String brand);

    // Verificar si existe producto con codigo
    boolean existsByIdProduct(String idProduct);

    // Buscar por marca y disponibilidad
    List<ElectronicsDTO> findByBrandAndIsAvailable(String brand, boolean isAvailable);
}