package com.example.services.serializers.book;

import com.example.core.entities.book.dtos.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookJsonSerializer {
    public static String serialize(BookDTO b) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonBook;
        try {
            jsonBook = mapper.writeValueAsString(b);
            return jsonBook;

        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static BookDTO deserialize(String s) throws Exception {
        try {
            BookDTO bImported = new ObjectMapper().readValue(s, BookDTO.class);
            return bImported;
        }catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }
    }
}
