package com.example.sharedkernel;

import com.example.core.entities.client.dtos.ClientDTO;
import com.example.services.serializers.XmlJacksonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("xmljacksonserializer (xml) - serializacion y deserializacion")
class JacksonXMLSerializerTest extends JacksonSerializerTest {

    @Nested
    @DisplayName("serialize - objeto a xml")
    class SerializeXml {

        private XmlJacksonSerializer<ClientDTO> xmlSerializer;

        @BeforeEach
        void init() {
            xmlSerializer = new XmlJacksonSerializer<>();
        }

        @Test
        void serializeProducesNonEmptyXml() throws Exception {
            String xml = xmlSerializer.serialize(clientDTO);
            assertNotNull(xml);
            assertFalse(xml.isEmpty());
        }

        @Test
        void xmlStartsWithRootTag() throws Exception {
            String xml = xmlSerializer.serialize(clientDTO);
            assertTrue(xml.startsWith("<") || xml.contains("ClientDTO"));
        }

        @Test
        void xmlContainsEmail() throws Exception {
            String xml = xmlSerializer.serialize(clientDTO);
            assertTrue(xml.contains("test@example.com"));
        }
    }

    @Nested
    @DisplayName("deserialize - xml a objeto")
    class DeserializeXml {

        private XmlJacksonSerializer<ClientDTO> xmlSerializer;

        @BeforeEach
        void init() {
            xmlSerializer = new XmlJacksonSerializer<>();
        }

        @Test
        void roundTripXmlProducesEqualObject() throws Exception {
            String xml = xmlSerializer.serialize(clientDTO);
            ClientDTO restored = xmlSerializer.deserialize(xml, ClientDTO.class);

            assertAll(
                    () -> assertEquals(clientDTO.getId(), restored.getId()),
                    () -> assertEquals(clientDTO.getEmail(), restored.getEmail()),
                    () -> assertEquals(clientDTO.getNamePerson(), restored.getNamePerson()));
        }

        @Test
        void throwsOnMalformedXml() {
            assertThrows(Exception.class,
                    () -> xmlSerializer.deserialize("<esto>no cierra", ClientDTO.class));
        }
    }
}