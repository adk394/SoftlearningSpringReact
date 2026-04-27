package com.example.order;

import com.example.core.entities.order.appservices.OrderServicesImpl;
import com.example.core.entities.order.dtos.OrderDTO;
import com.example.infraestructure.persistence.jpa.JpaOrderRepository;
import com.example.services.serializers.Serializer;
import com.example.services.serializers.Serializers;
import com.example.services.serializers.SerializersCatalog;
import com.example.shared.exceptions.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("orderservicesimpl")
class OrderServicesImplTest {

    @Mock
    private JpaOrderRepository orderRepository;

    @Mock
    private Serializer<OrderDTO> serializer;

    @InjectMocks
    private OrderServicesImpl orderServices;

    // Test data
    private static final int VALID_ORDER_ID = 1001;
    private static final int VALID_CLIENT_ID = 42;
    private static final String VALID_RECEIVER_ADDRESS = "Calle Agua 123";
    private static final String VALID_RECEIVER_PERSON = "Maria Garcia";
    private static final String VALID_PAYMENT_DATE = "2024-04-20T10:00:00";
    private static final String VALID_DELIVERY_DATE = "2024-04-25T10:00:00";
    private static final String VALID_PHONE_CONTACT = "+34 900111222";
    private static final String VALID_STATUS = "pending";
    private static final String VALID_START_DATE = "2024-04-20T09:00:00";
    private static final String VALID_DESCRIPTION = "Pedido de prueba";
    private static final String VALID_PACKAGE_DIMENSIONS = "30x20x10";

    private OrderDTO orderDTO;
    private String orderJson;

    @BeforeEach
    void setUp() {
        orderDTO = new OrderDTO(VALID_ORDER_ID, VALID_CLIENT_ID, VALID_RECEIVER_ADDRESS,
                VALID_RECEIVER_PERSON, VALID_PAYMENT_DATE, VALID_DELIVERY_DATE,
                VALID_PHONE_CONTACT, VALID_STATUS, VALID_START_DATE, VALID_DESCRIPTION,
                VALID_PACKAGE_DIMENSIONS, new ArrayList<>());
        orderJson = "{\"orderID\":1001,\"clientID\":42,\"recieverAddress\":\"Calle Agua 123\",\"recieverPerson\":\"Maria Garcia\",\"paymentDate\":\"2024-04-20T10:00:00\",\"deliveryDate\":\"2024-04-25T10:00:00\",\"phoneContact\":\"+34 900111222\",\"status\":\"pending\",\"startDate\":\"2024-04-20T09:00:00\",\"description\":\"Pedido de prueba\",\"packageDimensionsCsv\":\"30x20x10\",\"shopCart\":[]}";
    }

    @Nested
    @DisplayName("getByIdToJson")
    class GetByIdToJson {
        @Test
        @DisplayName("devuelve json cuando existe")
        void returnsJsonWhenExists() throws Exception {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.of(orderDTO));

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_ORDER)).thenReturn(serializer);
                when(serializer.serialize(orderDTO)).thenReturn(orderJson);

                String result = orderServices.getByIdToJson(VALID_ORDER_ID);

                assertEquals(orderJson, result);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando no existe")
        void throwsExceptionWhenNotExists() {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class, () -> orderServices.getByIdToJson(VALID_ORDER_ID));
            assertTrue(exception.getMessage().contains("Error serializando pedido a JSON"));
        }
    }

    @Nested
    @DisplayName("addFromJson")
    class AddFromJson {
        @Test
        @DisplayName("añade pedido correctamente")
        void addsOrderSuccessfully() throws Exception {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.empty());
            when(orderRepository.save(orderDTO)).thenReturn(orderDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_ORDER)).thenReturn(serializer);
                when(serializer.deserialize(orderJson, OrderDTO.class)).thenReturn(orderDTO);
                when(serializer.serialize(orderDTO)).thenReturn(orderJson);

                String result = orderServices.addFromJson(orderJson);

                assertEquals(orderJson, result);
                verify(orderRepository).save(orderDTO);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando pedido ya existe")
        void throwsExceptionWhenOrderExists() throws Exception {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.of(orderDTO));

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_ORDER)).thenReturn(serializer);
                when(serializer.deserialize(orderJson, OrderDTO.class)).thenReturn(orderDTO);

                ServiceException exception = assertThrows(ServiceException.class, () -> orderServices.addFromJson(orderJson));
                assertTrue(exception.getMessage().contains("Error añadiendo pedido desde JSON"));
            }
        }
    }

    @Nested
    @DisplayName("updateOneFromJson")
    class UpdateOneFromJson {
        @Test
        @DisplayName("actualiza pedido correctamente")
        void updatesOrderSuccessfully() throws Exception {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.of(orderDTO));
            when(orderRepository.save(orderDTO)).thenReturn(orderDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_ORDER)).thenReturn(serializer);
                when(serializer.deserialize(orderJson, OrderDTO.class)).thenReturn(orderDTO);
                when(serializer.serialize(orderDTO)).thenReturn(orderJson);

                String result = orderServices.updateOneFromJson(orderJson);

                assertEquals(orderJson, result);
                verify(orderRepository).save(orderDTO);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando pedido no existe")
        void throwsExceptionWhenOrderNotExists() throws Exception {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.empty());

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_ORDER)).thenReturn(serializer);
                when(serializer.deserialize(orderJson, OrderDTO.class)).thenReturn(orderDTO);

                ServiceException exception = assertThrows(ServiceException.class, () -> orderServices.updateOneFromJson(orderJson));
                assertTrue(exception.getMessage().contains("Error actualizando pedido desde JSON"));
            }
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {
        @Test
        @DisplayName("elimina pedido correctamente")
        void deletesOrderSuccessfully() throws ServiceException {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.of(orderDTO));

            orderServices.deleteById(VALID_ORDER_ID);

            verify(orderRepository).deleteById(VALID_ORDER_ID);
        }

        @Test
        @DisplayName("lanza excepcion cuando pedido no existe")
        void throwsExceptionWhenOrderNotExists() {
            when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class, () -> orderServices.deleteById(VALID_ORDER_ID));
            assertTrue(exception.getMessage().contains("no encontrado"));
        }
    }
}