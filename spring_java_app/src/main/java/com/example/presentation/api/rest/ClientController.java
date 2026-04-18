package com.example.presentation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.core.entities.client.dtos.ClientDTO;
import com.example.infraestructure.persistence.jpa.JpaClientRepository;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private JpaClientRepository clientRepository;

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll();
    }

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable int id, @RequestBody ClientDTO client) {
        client.setId(id);
        return clientRepository.save(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id) {
        clientRepository.deleteById(id);
    }

}