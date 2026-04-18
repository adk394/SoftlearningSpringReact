package com.example.services.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XmlJacksonSerializer<T> implements Serializer<T> {

    private XmlMapper mapper;

    public XmlJacksonSerializer() {
        mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ajustado para compatibilidad con localdate
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
