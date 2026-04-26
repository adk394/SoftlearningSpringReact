package com.example.book;

import com.example.core.entities.book.model.Book;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("book")
class BookTest {

    // datos validos reutilizables
    static final String VALID_ID = "1001";
    static final String VALID_NAME = "Clean Code";
    static final String VALID_DESCRIPTION = "Un libro sobre buenas prácticas";
    static final double VALID_PRICE = 29.99;
    static final int VALID_STOCK = 10;
    static final String VALID_ISBN = "978-3-16-148410-0";
    static final String VALID_TITLE = "Clean Code";
    static final String VALID_AUTHOR = "Robert C. Martin";
    static final String VALID_EDITORIAL = "Prentice Hall";
    static final int VALID_YEAR = 2008;
    static final double VALID_WEIGHT = 0.5;
    static final double VALID_HEIGHT = 23.0;
    static final double VALID_WIDTH = 15.0;
    static final double VALID_DEPTH = 2.0;

    Book book;

    @BeforeEach
    void setUp() throws BuildException {
        book = Book.getInstance(VALID_ID, VALID_NAME, VALID_DESCRIPTION, VALID_PRICE,
                VALID_STOCK, true, VALID_ISBN, VALID_TITLE, VALID_AUTHOR,
                VALID_EDITORIAL, VALID_YEAR, VALID_WEIGHT, VALID_HEIGHT,
                VALID_WIDTH, VALID_DEPTH);
    }

    @Nested
    @DisplayName("getinstance valido")
    class GetInstanceValid {
        @Test
        @DisplayName("asigna correctamente todos los atributos de product")
        void productAttributesSet() {
            assertAll(
                    () -> assertEquals(VALID_ID, book.getIdProduct()),
                    () -> assertEquals(VALID_NAME, book.getName()),
                    () -> assertEquals(VALID_PRICE, book.getPrice()),
                    () -> assertEquals(VALID_STOCK, book.getStock()));
        }

        @Test
        @DisplayName("asigna correctamente los atributos propios de book")
        void bookAttributesAreSet() {
            assertAll(
                    () -> assertEquals(VALID_ISBN, book.getIsbn()),
                    () -> assertEquals(VALID_TITLE, book.getTitle()),
                    () -> assertEquals(VALID_AUTHOR, book.getAuthor()),
                    () -> assertEquals(VALID_EDITORIAL, book.getEditorial()),
                    () -> assertEquals(VALID_YEAR, book.getYearPublished()));
        }

        @Test
        @DisplayName("physicaldata no es null y tiene los valores correctos")
        void physicalDataIsSet() {
            assertNotNull(book.getPhysicalData());
            assertEquals(VALID_WEIGHT, book.getPhysicalData().getWeight());
        }
    }

    @Nested
    @DisplayName("getinstance inválido")
    class GetInstanceInvalid {
        @Test
        @DisplayName("isbn invalido lanza BuildException")
        void badIsbnThrows() {
            assertThrows(BuildException.class,
                    () -> Book.getInstance(VALID_ID, VALID_NAME, VALID_DESCRIPTION, VALID_PRICE,
                            VALID_STOCK, true, "ISBN-MALO", VALID_TITLE, VALID_AUTHOR,
                            VALID_EDITORIAL, VALID_YEAR, VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH));
        }

        @Test
        @DisplayName("precio negativo lanza BuildException")
        void negativePriceThrows() {
            assertThrows(BuildException.class,
                    () -> Book.getInstance(VALID_ID, VALID_NAME, VALID_DESCRIPTION, -1.0,
                            VALID_STOCK, true, VALID_ISBN, VALID_TITLE, VALID_AUTHOR,
                            VALID_EDITORIAL, VALID_YEAR, VALID_WEIGHT, VALID_HEIGHT, VALID_WIDTH, VALID_DEPTH));
        }

        @Test
        @DisplayName("dimensiones fisicas <= 0 lanzan BuildException")
        void badPhysicalDataThrows() {
            assertThrows(BuildException.class,
                    () -> Book.getInstance(VALID_ID, VALID_NAME, VALID_DESCRIPTION, VALID_PRICE,
                            VALID_STOCK, true, VALID_ISBN, VALID_TITLE, VALID_AUTHOR,
                            VALID_EDITORIAL, VALID_YEAR, 0, 0, 0, 0));
        }
    }

    @Nested
    @DisplayName("validaciones de storable")
    class StorableMetods {
        @Test
        @DisplayName("getvolume delega en physicaldata")
        void volumeIsCorrect() {
            double expected = VALID_HEIGHT * VALID_WIDTH * VALID_DEPTH;
            assertEquals(expected, book.getVolume(), 0.001);
        }

        @Test
        @DisplayName("getarea delega en physicaldata")
        void areaIsCorrect() {
            assertEquals(VALID_WIDTH * VALID_HEIGHT, book.getArea(), 0.001);
        }
    }

    @Nested
    @DisplayName("validaciones de marketable")
    class MarketableMethods {
        @Test
        @DisplayName("isavailable devuelve true cuando se crea con isavailable=true")
        void isAvailableTrue() {
            assertTrue(book.isAvailable());
        }

        @Test
        @DisplayName("setavailable cambia la disponibilidad")
        void changeAvailability() {
            book.setAvailable(false);
            assertFalse(book.isAvailable());
        }
    }

    @Nested
    @DisplayName("setters de PRODUCT (heredados)")
    class ProductSetters {
        @Test
        @DisplayName("setprice acepta precio >= 0")
        void setPriceValid() {
            assertEquals(0, book.setPrice(15.0));
            assertEquals(15.0, book.getPrice());
        }

        @Test
        @DisplayName("setprice rechaza precio negativo")
        void setPriceInvalid() {
            assertEquals(-1, book.setPrice(-1.0));
        }

        @Test
        @DisplayName("setstock acepta stock >= 0")
        void setStockValid() {
            assertEquals(0, book.setStock(0));
        }
    }

    @Nested
    @DisplayName("setters de BOOK")
    class BookSetters {
        @Test
        @DisplayName("isbn ok")
        void setIsbnValid() {
            assertEquals(0, book.setIsbn(VALID_ISBN));
            assertEquals(VALID_ISBN, book.getIsbn());
        }

        @Test
        @DisplayName("isbn bad")
        void setIsbnInvalid() {
            assertEquals(-1, book.setIsbn("BAD-ISBN"));
        }

        @Test
        @DisplayName("title ok")
        void setTitleValid() {
            assertEquals(0, book.setTitle("jarry potter"));
            assertEquals("jarry potter", book.getTitle());
        }

        @Test
        @DisplayName("title bad")
        void setTitleInvalid() {
            assertEquals(-1, book.setTitle(""));
        }

        @Test
        @DisplayName("author ok")
        void setAuthorValid() {
            assertEquals(0, book.setAuthor("Author"));
            assertEquals("Author", book.getAuthor());
        }

        @Test
        @DisplayName("author bad")
        void setAuthorInvalid() {
            assertEquals(-1, book.setAuthor(""));
        }

        @Test
        @DisplayName("editorial ok")
        void setEditorialValid() {
            assertEquals(0, book.setEditorial("Ed"));
            assertEquals("Ed", book.getEditorial());
        }

        @Test
        @DisplayName("editorial bad")
        void setEditorialInvalid() {
            assertEquals(-1, book.setEditorial("A"));
        }

        @Test
        @DisplayName("year ok")
        void setYearValid() {
            assertEquals(0, book.setYearPublished(1999));
            assertEquals(1999, book.getYearPublished());
        }

    }
}