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
@DisplayName("clientservice")
public class ClientServiceTest {

    @Mock
    private JpaClientRepository clientRepository;

    @Mock
    private Serializer<ClientDTO> serializer;

    @InjectMocks
    private ClientServicesImpl clientServices;

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

    @Test
    @DisplayName("ciclo completo de crud")
    void testClientServicesCrudLifecycle() throws Exception {
        when(clientRepository.findById(0)).thenReturn(java.util.Optional.empty());
        when(clientRepository.save(any(ClientDTO.class))).thenReturn(clientDTO);

        try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
            mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_CLIENT))
                    .thenReturn(serializer);

            // add
            when(serializer.serialize(any(ClientDTO.class))).thenReturn(clientJson);
            String addedJson = clientServices.addFromJson(clientJson);
            assertNotNull(addedJson);

            // get
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.of(clientDTO));
            when(serializer.serialize(clientDTO)).thenReturn(clientJson);
            String retrievedJson = clientServices.getByIdToJson(0);
            assertNotNull(retrievedJson);

            // update
            when(clientRepository.save(any(ClientDTO.class))).thenReturn(clientDTO);
            String updatedJson = clientServices.updateOneFromJson(clientJson);
            assertNotNull(updatedJson);

            // delete
            clientServices.deleteById(0);
            verify(clientRepository).deleteById(0);

            // verificar que ya no existe
            when(clientRepository.findById(0)).thenReturn(java.util.Optional.empty());
            assertThrows(ServiceException.class, () -> clientServices.getByIdToJson(0));
        }
    }
}