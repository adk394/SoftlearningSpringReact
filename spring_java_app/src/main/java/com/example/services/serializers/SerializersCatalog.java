package com.example.services.serializers;

import java.util.TreeMap;

import com.example.core.entities.client.dtos.ClientDTO;
import com.example.core.entities.client.dtos.SpanishClientDTO;
import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.book.dtos.SpanishBookDTO;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.core.entities.order.dtos.SpanishOrderDTO;

public class SerializersCatalog {
    private static TreeMap<Serializers, Serializer<?>> catalog = new TreeMap<>();

    private static void loadCatalog() {
        // clients
        catalog.put(Serializers.JSON_CLIENT, new JacksonSerializer<ClientDTO>());
        catalog.put(Serializers.JSON_SP_CLIENT, new JacksonSerializer<SpanishClientDTO>());
        catalog.put(Serializers.XML_CLIENT, new XmlJacksonSerializer<ClientDTO>());
        catalog.put(Serializers.XML_SP_CLIENT, new XmlJacksonSerializer<SpanishClientDTO>());
        // books
        catalog.put(Serializers.JSON_BOOK, new JacksonSerializer<BookDTO>());
        catalog.put(Serializers.JSON_SP_BOOK, new JacksonSerializer<SpanishBookDTO>());
        catalog.put(Serializers.XML_BOOK, new XmlJacksonSerializer<BookDTO>());
        catalog.put(Serializers.XML_SP_BOOK, new XmlJacksonSerializer<SpanishBookDTO>());
        // orders
        catalog.put(Serializers.JSON_ORDER, new JacksonSerializer<OrderDTO>());
        catalog.put(Serializers.JSON_SP_ORDER, new JacksonSerializer<SpanishOrderDTO>());
        catalog.put(Serializers.XML_ORDER, new XmlJacksonSerializer<OrderDTO>());
        catalog.put(Serializers.XML_SP_ORDER, new XmlJacksonSerializer<SpanishOrderDTO>());
    }

    @SuppressWarnings("unchecked")
    public static <T> Serializer<T> getInstance(Serializers type) {
        if (catalog.isEmpty()) {
            loadCatalog();
        }
        return (Serializer<T>) catalog.get(type);
    }
}
