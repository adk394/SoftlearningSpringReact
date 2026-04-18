package com.example.services.serializers.book;

import com.example.core.entities.order.dtos.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderJsonSerializer {

    public static String serialize(OrderDTO o) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static OrderDTO deserialize(String s) throws Exception {
        try {
            return new ObjectMapper().readValue(s, OrderDTO.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}
