package com.example.services.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class JacksonSerializer<T> implements Serializer<T> {

    private ObjectMapper mapper;

    public JacksonSerializer() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // ajustado para compatibilidad con localdate
        mapper.findAndRegisterModules();
    }

    @Override
    public String serialize(T object) throws Exception {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public T deserialize(String source, Class<T> object) throws Exception {
        try {
            return mapper.readValue(source, object);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}