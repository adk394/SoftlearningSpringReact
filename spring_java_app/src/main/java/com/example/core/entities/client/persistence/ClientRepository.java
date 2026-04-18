package com.example.core.entities.client.persistence;

import java.util.Optional;
import com.example.core.entities.client.dtos.ClientDTO;

public interface ClientRepository {

    public Optional<ClientDTO> findById(int id);

    public ClientDTO save(ClientDTO client);

    public void deleteById(int id);
}
