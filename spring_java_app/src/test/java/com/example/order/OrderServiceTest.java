package com.example.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.entities.order.appservices.OrderServicesImpl;
import com.example.shared.exceptions.ServiceException;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.mode=never"
})
@DisplayName("orderservice")
public class OrderServiceTest {

    @Autowired
    private OrderServicesImpl orderServices;

    @Test
    @Transactional
    public void testOrderServicesCrudLifecycle() {
        // prueba rapida de crud

        // datos de prueba
        String orderJson = "{\"orderID\":1001,\"clientID\":42,\"recieverAddress\":\"Calle Agua 123\",\"recieverPerson\":\"Maria Garcia\",\"paymentDate\":\"2024-04-20T10:00:00\",\"deliveryDate\":\"2024-04-25T10:00:00\",\"phoneContact\":\"+34 900111222\",\"status\":\"pending\",\"startDate\":\"2024-04-20T09:00:00\",\"description\":\"Pedido de prueba\",\"packageDimensionsCsv\":\"30x20x10\",\"shopCart\":[]}";

        try {
            // add
            String addedJson = orderServices.addFromJson(orderJson);
            assertNotNull(addedJson);
            assertTrue(addedJson.contains("Pedido de prueba"));

            // get
            String retrievedJson = orderServices.getByIdToJson(1001);
            assertNotNull(retrievedJson);
            assertTrue(retrievedJson.contains("Pedido de prueba"));

            // update
            String updatedJson = orderServices.updateOneFromJson(orderJson);
            assertNotNull(updatedJson);
            assertTrue(updatedJson.contains("Pedido de prueba"));

            // delete
            orderServices.deleteById(1001);

            // verificar que ya no existe
            assertThrows(ServiceException.class, () -> orderServices.getByIdToJson(1001));

        } catch (ServiceException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}