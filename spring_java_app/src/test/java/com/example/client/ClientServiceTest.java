package com.example.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.entities.client.appservices.ClientServicesImpl;
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
@DisplayName("clientservice")
public class ClientServiceTest {

    @Autowired
    private ClientServicesImpl clientServices;

    @Test
    @Transactional
    public void testClientServicesCrudLifecycle() {

        // datos de prueba
        String clientJson = "{\"id\":0,\"idPerson\":\"12345678A\",\"email\":\"test@email.com\",\"phone\":\"600123456\",\"adress\":\"Calle Aigua 123\",\"namePerson\":\"Juan Perez\",\"registrationDate\":\"01-01-2024, 00:00:00\"}";

        try {
            // add
            String addedJson = clientServices.addFromJson(clientJson);
            assertNotNull(addedJson);

            // get
            String retrievedJson = clientServices.getByIdToJson(0);
            assertNotNull(retrievedJson);

        } catch (ServiceException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}