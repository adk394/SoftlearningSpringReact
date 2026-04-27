package com.example.client;

import com.example.core.entities.client.appservices.ClientServicesImpl;
import com.example.core.entities.client.dtos.ClientDTO;
import com.example.infraestructure.persistence.jpa.JpaClientRepository;
import com.example.services.serializers.Serializer;
import com.example.services.serializers.Serializers;
import com.example.services.serializers.SerializersCatalog;
import com.example.shared.exceptions.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("clientservicesimpl")
class ClientServicesImplTest {

    @Mock
    private JpaClientRepository clientRepository;

    @Mock
    private Serializer<ClientDTO> serializer;

    @InjectMocks
    private ClientServicesImpl clientServices;

    // Test data
    private static final int VALID_ID = 1001;
    private static final String VALID_ID_PERSON = "12345678A";
    private static final String VALID_EMAIL = "test@email.com";
    private static final String VALID_PHONE = "600123456";
    private static final String VALID_ADDRESS = "Calle Aigua 123";
    private static final String VALID_NAME = "Juan Perez";
    private static final String VALID_REG_DATE = "01-01-2024, 00:00:00";

    private ClientDTO clientDTO;
    private String clientJson;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO(VALID_ID, VALID_ID_PERSON, VALID_EMAIL, VALID_PHONE,
                VALID_ADDRESS, VALID_NAME, VALID_REG_DATE);
        clientJson = "{\"id\":1001,\"idPerson\":\"12345678A\",\"email\":\"test@email.com\",\"phone\":\"600123456\",\"adress\":\"Calle Aigua 123\",\"namePerson\":\"Juan Perez\",\"registrationDate\":\"01-01-2024, 00:00:00\"}";
    }

    @Nested
    @DisplayName("getByIdToJson")
    class GetByIdToJson {
        @Test
        @DisplayName("devuelve json cuando existe")
        void returnsJsonWhenExists() throws Exception {
            when(clientRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(clientDTO));

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_CLIENT))
                        .thenReturn(serializer);
                when(serializer.serialize(clientDTO)).thenReturn(clientJson);

                String result = clientServices.getByIdToJson(VALID_ID);

                assertEquals(clientJson, result);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando no existe")
        void throwsExceptionWhenNotExists() {
            when(clientRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class,
                    () -> clientServices.getByIdToJson(VALID_ID));
            assertTrue(exception.getMessage().contains("Error serializando cliente a JSON"));
        }
    }

    @Nested
    @DisplayName("addFromJson")
    class AddFromJson {
        @Test
        @DisplayName("añade cliente correctamente")
        void addsClientSuccessfully() throws Exception {
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.empty());
            when(clientRepository.save(any(ClientDTO.class))).thenReturn(clientDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_CLIENT))
                        .thenReturn(serializer);
                when(serializer.serialize(any(ClientDTO.class))).thenReturn(clientJson);

                String result = clientServices.addFromJson(clientJson);

                assertEquals(clientJson, result);
                verify(clientRepository).save(any(ClientDTO.class));
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando cliente ya existe")
        void throwsExceptionWhenClientExists() {
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.of(clientDTO));

            ServiceException exception = assertThrows(ServiceException.class,
                    () -> clientServices.addFromJson(clientJson));
            assertTrue(exception.getMessage().contains("Error añadiendo cliente desde JSON"));
        }
    }

    @Nested
    @DisplayName("updateOneFromJson")
    class UpdateOneFromJson {
        @Test
        @DisplayName("actualiza cliente correctamente")
        void updatesClientSuccessfully() throws Exception {
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.of(clientDTO));
            when(clientRepository.save(any(ClientDTO.class))).thenReturn(clientDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_CLIENT))
                        .thenReturn(serializer);
                when(serializer.serialize(any(ClientDTO.class))).thenReturn(clientJson);

                String result = clientServices.updateOneFromJson(clientJson);

                assertEquals(clientJson, result);
                verify(clientRepository).save(any(ClientDTO.class));
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando cliente no existe")
        void throwsExceptionWhenClientNotExists() {
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class,
                    () -> clientServices.updateOneFromJson(clientJson));
            assertTrue(exception.getMessage().contains("Error actualizando cliente desde JSON"));
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {
        @Test
        @DisplayName("elimina cliente correctamente")
        void deletesClientSuccessfully() throws ServiceException {
            when(clientRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(clientDTO));

            clientServices.deleteById(VALID_ID);

            verify(clientRepository).deleteById(VALID_ID);
        }

        @Test
        @DisplayName("lanza excepcion cuando cliente no existe")
        void throwsExceptionWhenClientNotExists() {
            when(clientRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class,
                    () -> clientServices.deleteById(VALID_ID));
            assertTrue(exception.getMessage().contains("no encontrado"));
        }
    }
}