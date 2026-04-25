package com.example.client;

import com.example.core.entities.client.model.Client;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("client - creacion y validacion")
class ClientTest {

    @Test
    void createValidClient() throws BuildException {
        Client client = Client.getInstance(
                "12345678A",
                "test@email.com",
                "600123456",
                "Calle Falsa 123",
                "juan perez",
                1001,
                "01-01-2024, 00:00:00");

        assertAll(
                () -> assertEquals(1001, client.getIdClient()),
                () -> assertEquals("test@email.com", client.getEmail()),
                () -> assertEquals("juan perez", client.getNamePerson()));
    }

    @Test
    void throwsWhenInvalidEmail() {
        assertThrows(BuildException.class, () -> Client.getInstance(
                "12345678A",
                "email-invalido",
                "600123456",
                "Calle Falsa 123",
                "juan perez",
                1001,
                "01-01-2024, 00:00:00"));
    }
}