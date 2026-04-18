package com.example.services.serializers.book;

import com.example.core.entities.client.dtos.ClientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientJsonSerializer {

    public static String serialize(ClientDTO c) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(c);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static ClientDTO deserialize(String s) throws Exception {
        try {
            return new ObjectMapper().readValue(s, ClientDTO.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}
