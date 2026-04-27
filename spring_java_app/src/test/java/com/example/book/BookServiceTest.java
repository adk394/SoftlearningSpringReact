package com.example.book;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.entities.book.appservices.BookServicesImpl;
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
@DisplayName("bookservice")
public class BookServiceTest {

    @Autowired
    private BookServicesImpl bookServices;

    @Test
    @Transactional
    public void testBookServicesCrudLifecycle() {

        // datos de prueba
        String bookJson = "{\"title\":\"Clean Code\",\"id\":1001,\"price\":29.99,\"author\":\"Robert C. Martin\",\"isbn\":\"978-3-16-148410-0\",\"releaseDate\":\"2008\",\"publisher\":\"Prentice Hall\",\"weight\":0.5,\"height\":23.0,\"width\":15.0,\"depth\":2.0}";

        try {
            // add
            String addedJson = bookServices.addFromJson(bookJson);
            assertNotNull(addedJson);
            assertTrue(addedJson.contains("Clean Code"));

            // get
            String retrievedJson = bookServices.getByIdToJson(1001);
            assertNotNull(retrievedJson);
            assertTrue(retrievedJson.contains("Clean Code"));

            // update
            String updatedJson = bookServices.updateOneFromJson(bookJson);
            assertNotNull(updatedJson);
            assertTrue(updatedJson.contains("Clean Code"));

            // delete
            bookServices.deleteById(1001);

            // verificar que ya no existe
            assertThrows(ServiceException.class, () -> bookServices.getByIdToJson(1001));

        } catch (ServiceException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}