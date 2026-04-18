package com.example.core.entities.client.mapper;

import com.example.core.entities.client.model.Client;
import com.example.core.entities.client.dtos.ClientDTO;
import com.example.core.entities.client.dtos.SpanishClientDTO;
import com.example.shared.exceptions.BuildException;

public class ClientMapper {

    // convierte client a dto normal
    public static ClientDTO dtoFromClient(Client client) {
        return new ClientDTO(
                client.getIdClient(),
                client.getIdPerson(),
                client.getEmail(),
                client.getPhone(),
                client.getAdress(),
                client.getNamePerson(),
                client.getRegistrationDate()
        );
    }

    // convierte dto normal a client
    public static Client clientFromDTO(ClientDTO dto) throws BuildException {
        return Client.getInstance(
                dto.getIdPerson(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAdress(),
                dto.getNamePerson(),
                dto.getId(),
                dto.getRegistrationDate()
        );
    }

    // convierte client a dto spanish
    public static SpanishClientDTO spanishDtoFromClient(Client client) {
        return new SpanishClientDTO(
                client.getIdClient(),
                client.getIdPerson(),
                client.getEmail(),
                client.getPhone(),
                client.getAdress(),
                client.getNamePerson(),
                client.getRegistrationDate()
        );
    }

    // convierte dto spanish a client
    public static Client clientFromSpanishDTO(SpanishClientDTO dto) throws BuildException {
        return Client.getInstance(
                dto.getIdPerson(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAdress(),
                dto.getNamePerson(),
                dto.getId(),
                dto.getRegistrationDate()
        );
    }
}
