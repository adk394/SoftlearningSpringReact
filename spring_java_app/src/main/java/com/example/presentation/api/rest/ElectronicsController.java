package com.example.presentation.api.rest;

import com.example.core.entities.electronics.dtos.ElectronicsDTO;
import com.example.core.entities.electronics.persistence.ElectronicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ElectronicsController - API REST con JWT (sin validacion de roles)
 * Requiere token JWT valido, pero no verifica roles especificos
 * URL base: /api/electronics
 */
@RestController
@RequestMapping("/api/electronics")
public class ElectronicsController {

    @Autowired
    private ElectronicsRepository electronicsRepository;

    // GET - Obtener todos
    @GetMapping
    public ResponseEntity<List<ElectronicsDTO>> getAllElectronics() {
        return ResponseEntity.ok(electronicsRepository.findAll());
    }

    // GET - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getElectronicsById(@PathVariable int id) {
        Optional<ElectronicsDTO> electronics = electronicsRepository.findById(id);
        
        if (electronics.isPresent()) {
            return ResponseEntity.ok(electronics.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + id + " no encontrado");
        }
    }

    // GET - Buscar por nombre (parcial)
    @GetMapping("/search")
    public ResponseEntity<List<ElectronicsDTO>> searchByName(
            @RequestParam(name = "name", required = false) String name) {
        
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(electronicsRepository.findByNameContaining(name));
        }
        return ResponseEntity.ok(electronicsRepository.findAll());
    }

    // GET - Buscar por marca
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ElectronicsDTO>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(electronicsRepository.findByBrand(brand));
    }

    // POST - Crear nuevo
    @PostMapping
    public ResponseEntity<?> createElectronics(@RequestBody ElectronicsDTO electronicsDTO) {
        try {
            // Verificar que no exista idProduct duplicado
            if (electronicsDTO.getIdProduct() != null && 
                electronicsRepository.existsByIdProduct(electronicsDTO.getIdProduct())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Ya existe producto con codigo " + electronicsDTO.getIdProduct());
            }

            electronicsDTO.setId(0);  // Asegurar que sea nuevo
            ElectronicsDTO saved = electronicsRepository.save(electronicsDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear: " + e.getMessage());
        }
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> updateElectronics(
            @PathVariable int id, 
            @RequestBody ElectronicsDTO electronicsDTO) {
        
        if (!electronicsRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + id + " no encontrado");
        }

        try {
            electronicsDTO.setId(id);
            ElectronicsDTO updated = electronicsRepository.save(electronicsDTO);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteElectronics(@PathVariable int id) {
        if (!electronicsRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + id + " no encontrado");
        }

        try {
            electronicsRepository.deleteById(id);
            return ResponseEntity.ok("Producto con ID " + id + " eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar: " + e.getMessage());
        }
    }
}