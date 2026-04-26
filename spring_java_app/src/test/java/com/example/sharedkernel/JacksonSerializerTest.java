package com.example.sharedkernel;

import com.example.core.entities.client.dtos.ClientDTO;
import com.example.services.serializers.JacksonSerializer;
import com.example.services.serializers.XmlJacksonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("serializar JSON")
class JacksonSerializerTest {

    protected ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO(
                1001,
                "12345678A",
                "test@example.com",
                "600123456",
                "Calle Mayor 10, Madrid",
                "Juan Test",
                "01-01-2024, 10:00:00");
    }

    @Nested
    @DisplayName("serializar")
    class Serialize {

        private JacksonSerializer<ClientDTO> serializer;

        @BeforeEach
        void init() {
            serializer = new JacksonSerializer<>();
        }

        @Test
        void serializeProducesNonEmptyJson() throws Exception {
            String json = serializer.serialize(clientDTO);
            assertNotNull(json);
            assertFalse(json.isEmpty());
        }

        @Test
        void jsonContainsEmail() throws Exception {
            String json = serializer.serialize(clientDTO);
            assertTrue(json.contains("test@example.com"));
        }

        @Test
        void jsonContainsId() throws Exception {
            String json = serializer.serialize(clientDTO);
            assertTrue(json.contains("1001"));
        }
    }

    @Nested
    @DisplayName("deserializar")
    class Deserialize {

        private JacksonSerializer<ClientDTO> serializer;

        @BeforeEach
        void init() {
            serializer = new JacksonSerializer<>();
        }

        @Test
        void roundTripProducesEqualObject() throws Exception {
            String json = serializer.serialize(clientDTO);
            ClientDTO restored = serializer.deserialize(json, ClientDTO.class);

            assertAll(
                    () -> assertEquals(clientDTO.getId(), restored.getId()),
                    () -> assertEquals(clientDTO.getEmail(), restored.getEmail()),
                    () -> assertEquals(clientDTO.getNamePerson(), restored.getNamePerson()),
                    () -> assertEquals(clientDTO.getPhone(), restored.getPhone()));
        }

        @Test
        void throwsOnMalformedJson() {
            assertThrows(Exception.class,
                    () -> serializer.deserialize("{esto no es json}", ClientDTO.class));
        }
    }
}