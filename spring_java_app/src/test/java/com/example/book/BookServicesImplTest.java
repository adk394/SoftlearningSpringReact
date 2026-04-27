package com.example.book;

import com.example.core.entities.book.appservices.BookServicesImpl;
import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.book.persistence.BookRepository;
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
@DisplayName("bookservicesimpl")
class BookServicesImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Serializer<BookDTO> serializer;

    @InjectMocks
    private BookServicesImpl bookServices;

    // Test data
    private static final int VALID_ID = 1001;
    private static final String VALID_TITLE = "Clean Code";
    private static final double VALID_PRICE = 29.99;
    private static final String VALID_AUTHOR = "Robert C. Martin";
    private static final String VALID_ISBN = "978-3-16-148410-0";
    private static final String VALID_RELEASE_DATE = "2008";
    private static final String VALID_PUBLISHER = "Prentice Hall";
    private static final double VALID_WEIGHT = 0.5;
    private static final double VALID_HEIGHT = 23.0;
    private static final double VALID_WIDTH = 15.0;
    private static final double VALID_DEPTH = 2.0;

    private BookDTO bookDTO;
    private String bookJson;

    @BeforeEach
    void setUp() {
        bookDTO = new BookDTO(VALID_TITLE, VALID_ID, VALID_PRICE, VALID_AUTHOR, VALID_ISBN,
                VALID_RELEASE_DATE, VALID_PUBLISHER, VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
        bookJson = "{\"title\":\"Clean Code\",\"id\":1001,\"price\":29.99,\"author\":\"Robert C. Martin\",\"isbn\":\"978-3-16-148410-0\",\"releaseDate\":\"2008\",\"publisher\":\"Prentice Hall\",\"weight\":0.5,\"height\":23.0,\"width\":15.0,\"depth\":2.0}";
    }

    @Nested
    @DisplayName("getByIdToJson")
    class GetByIdToJson {
        @Test
        @DisplayName("devuelve json cuando existe")
        void returnsJsonWhenExists() throws Exception {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(bookDTO));

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_BOOK)).thenReturn(serializer);
                when(serializer.serialize(bookDTO)).thenReturn(bookJson);

                String result = bookServices.getByIdToJson(VALID_ID);

                assertEquals(bookJson, result);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando no existe")
        void throwsExceptionWhenNotExists() {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class,
                    () -> bookServices.getByIdToJson(VALID_ID));
            assertTrue(exception.getMessage().contains("Error serializando el libro"));
        }
    }

    @Nested
    @DisplayName("addFromJson")
    class AddFromJson {
        @Test
        @DisplayName("añade libro correctamente")
        void addsBookSuccessfully() throws Exception {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());
            when(bookRepository.save(bookDTO)).thenReturn(bookDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_BOOK)).thenReturn(serializer);
                when(serializer.deserialize(bookJson, BookDTO.class)).thenReturn(bookDTO);
                when(serializer.serialize(bookDTO)).thenReturn(bookJson);

                String result = bookServices.addFromJson(bookJson);

                assertEquals(bookJson, result);
                verify(bookRepository).save(bookDTO);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando libro ya existe")
        void throwsExceptionWhenBookExists() throws Exception {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(bookDTO));

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_BOOK)).thenReturn(serializer);
                when(serializer.deserialize(bookJson, BookDTO.class)).thenReturn(bookDTO);

                ServiceException exception = assertThrows(ServiceException.class,
                        () -> bookServices.addFromJson(bookJson));
                assertTrue(exception.getMessage().contains("Error añadiendo libro desde JSON"));
            }
        }
    }

    @Nested
    @DisplayName("updateOneFromJson")
    class UpdateOneFromJson {
        @Test
        @DisplayName("actualiza libro correctamente")
        void updatesBookSuccessfully() throws Exception {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(bookDTO));
            when(bookRepository.save(bookDTO)).thenReturn(bookDTO);

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_BOOK)).thenReturn(serializer);
                when(serializer.deserialize(bookJson, BookDTO.class)).thenReturn(bookDTO);
                when(serializer.serialize(bookDTO)).thenReturn(bookJson);

                String result = bookServices.updateOneFromJson(bookJson);

                assertEquals(bookJson, result);
                verify(bookRepository).save(bookDTO);
            }
        }

        @Test
        @DisplayName("lanza excepcion cuando libro no existe")
        void throwsExceptionWhenBookNotExists() throws Exception {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());

            try (MockedStatic<SerializersCatalog> mockedCatalog = Mockito.mockStatic(SerializersCatalog.class)) {
                mockedCatalog.when(() -> SerializersCatalog.getInstance(Serializers.JSON_BOOK)).thenReturn(serializer);
                when(serializer.deserialize(bookJson, BookDTO.class)).thenReturn(bookDTO);

                ServiceException exception = assertThrows(ServiceException.class,
                        () -> bookServices.updateOneFromJson(bookJson));
                assertTrue(exception.getMessage().contains("Error actualizando libro desde JSON"));
            }
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {
        @Test
        @DisplayName("elimina libro correctamente")
        void deletesBookSuccessfully() throws ServiceException {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.of(bookDTO));

            bookServices.deleteById(VALID_ID);

            verify(bookRepository).deleteById(VALID_ID);
        }

        @Test
        @DisplayName("lanza excepcion cuando libro no existe")
        void throwsExceptionWhenBookNotExists() {
            when(bookRepository.findById(VALID_ID)).thenReturn(java.util.Optional.empty());

            ServiceException exception = assertThrows(ServiceException.class, () -> bookServices.deleteById(VALID_ID));
            assertTrue(exception.getMessage().contains("not found"));
        }
    }
}