package com.example.client;

import com.example.core.entities.client.model.Client;
import com.example.core.entities.client.dtos.ClientDTO;
import com.example.core.entities.client.mapper.ClientMapper;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("clientmapper")
class ClientMapperTest {

        @Test
        void dtoToEntityMapsCorrectly() throws BuildException {
                ClientDTO dto = new ClientDTO(
                                1001,
                                "12345678A",
                                "test@email.com",
                                "600123456",
                                "Calle Falsa 123",
                                "juan perez",
                                "01-01-2024, 00:00:00");

                Client client = ClientMapper.clientFromDTO(dto);

                assertAll(
                                () -> assertEquals(dto.getId(), client.getIdClient()),
                                () -> assertEquals(dto.getEmail(), client.getEmail()),
                                () -> assertEquals(dto.getNamePerson(), client.getNamePerson()));
        }

        @Test
        void entityToDtoMapsCorrectly() throws BuildException {
                Client client = Client.getInstance(
                                "12345678A",
                                "test@email.com",
                                "600123456",
                                "Calle Falsa 123",
                                "Juan Pérez",
                                1001,
                                "01-01-2024, 00:00:00");

                ClientDTO dto = ClientMapper.dtoFromClient(client);

                assertAll(
                                () -> assertEquals(client.getIdClient(), dto.getId()),
                                () -> assertEquals(client.getEmail(), dto.getEmail()),
                                () -> assertEquals(client.getNamePerson(), dto.getNamePerson()));
        }
}