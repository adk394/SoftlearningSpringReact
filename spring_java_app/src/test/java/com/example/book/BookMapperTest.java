package com.example.book;

import com.example.core.entities.book.dtos.BookDTO;
import com.example.core.entities.book.mapper.BookMapper;
import com.example.core.entities.book.model.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("bookmapper - conversion entre book y dto")
class BookMapperTest extends BookTest {

    // hereda setup() de BookTest q inicializa 'book'
    private BookDTO dto;

    @BeforeEach
    void setUpDto() {
        dto = new BookDTO(VALID_TITLE, Integer.parseInt(VALID_ID), VALID_PRICE,
                VALID_AUTHOR, VALID_ISBN, String.valueOf(VALID_YEAR),
                VALID_EDITORIAL, VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH);
    }

    @Nested
    @DisplayName("booktodto - book -> dto")
    class BookToDTO {
        @Test
        @DisplayName("convierte book a dto con todos los campos mapeados")
        void convertsAllFields() {
            BookDTO result = BookMapper.BookToDTO(book);
            assertAll(
                    () -> assertEquals(book.getTitle(), result.getTitle()),
                    () -> assertEquals(book.getIsbn(), result.getIsbn()),
                    () -> assertEquals(book.getAuthor(), result.getAuthor()),
                    () -> assertEquals(book.getPrice(), result.getPrice()),
                    () -> assertEquals(book.getEditorial(), result.getPublisher()),
                    () -> assertEquals(book.getPhysicalData().getWeight(), result.getWeight()));
        }
    }

    @Nested
    @DisplayName("dtotobook - dto -> book")
    class DtoToBook {
        @Test
        @DisplayName("convierte dto valido a book con datos correctos")
        void convertsDtoToBook() throws Exception {
            Book result = BookMapper.DTOtoBook(dto);
            assertAll(
                    () -> assertEquals(dto.getTitle(), result.getTitle()),
                    () -> assertEquals(dto.getIsbn(), result.getIsbn()),
                    () -> assertEquals(dto.getAuthor(), result.getAuthor()),
                    () -> assertEquals(dto.getPrice(), result.getPrice()));
        }

        @Test
        @DisplayName("lanza excepcion si el dto tiene isbn invalido")
        void throwsOnBadIsbn() {
            dto.setIsbn("INVALIDO");
            assertThrows(Exception.class, () -> BookMapper.DTOtoBook(dto));
        }
    }

    @Nested
    @DisplayName("round-trip book -> dto -> book")
    class RoundTrip {
        @Test
        @DisplayName("el round-trip conserva titulo, isbn y precio")
        void roundTripPreservesData() throws Exception {
            BookDTO converted = BookMapper.BookToDTO(book);
            Book restored = BookMapper.DTOtoBook(converted);
            assertAll(
                    () -> assertEquals(book.getTitle(), restored.getTitle()),
                    () -> assertEquals(book.getIsbn(), restored.getIsbn()),
                    () -> assertEquals(book.getPrice(), restored.getPrice()));
        }
    }
}