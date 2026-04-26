package com.example.order;

import com.example.core.entities.order.model.OrderDetail;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("orderdetail")
class OrderDetailTest {

    // datos validos
    static final String VALID_REF = "BOOK-001";
    static final double VALID_PRICE = 29.99;
    static final double VALID_DISCOUNT = 5.0;
    static final int VALID_AMOUNT = 2;

    OrderDetail detail;

    @BeforeEach
    void setUp() {
        detail = new OrderDetail(VALID_REF, VALID_PRICE, VALID_DISCOUNT, VALID_AMOUNT);
    }

    @Nested
    @DisplayName("constructor y getters")
    class ConstructorAndGetters {
        @Test
        @DisplayName("asigna correctamente todos los campos")
        void allFieldsAreSet() {
            assertAll(
                    () -> assertEquals(VALID_REF, detail.getRef()),
                    () -> assertEquals(VALID_PRICE, detail.getPrice()),
                    () -> assertEquals(VALID_DISCOUNT, detail.getDiscount()),
                    () -> assertEquals(VALID_AMOUNT, detail.getAmount()));
        }
    }

    @Nested
    @DisplayName("coste detalle")
    class DetailCost {
        @Test
        @DisplayName("calcula coste")
        void costIsCorrect() {
            double expected = (VALID_PRICE - VALID_DISCOUNT) * VALID_AMOUNT;
            assertEquals(expected, detail.getDetailCost(), 0.001);
        }

        @Test
        @DisplayName("con descuento 0 el coste es price * amount")
        void costWithNoDiscount() {
            OrderDetail d = new OrderDetail(VALID_REF, 10.0, 0.0, 3);
            assertEquals(30.0, d.getDetailCost(), 0.001);
        }
    }

    @Nested
    @DisplayName("setamount")
    class SetAmount {
        @Test
        @DisplayName("setamount acepta 0 y valores positivos")
        void acceptsZeroPositive() throws BuildException {
            detail.setAmount(0);
            assertEquals(0, detail.getAmount());
        }

        @Test
        @DisplayName("setamount lanza BuildException con valor negativo")
        void throwsOnNegative() {
            assertThrows(BuildException.class, () -> detail.setAmount(-1));
        }
    }

    @Nested
    @DisplayName("formato csv")
    class GetDetail {
        @Test
        @DisplayName("getdetail devuelve los 4 campos separados por coma")
        void detailHasAllFields() {
            String d = detail.getDetail();
            assertAll(
                    () -> assertTrue(d.contains(VALID_REF)),
                    () -> assertTrue(d.contains(String.valueOf(VALID_PRICE))),
                    () -> assertTrue(d.contains(String.valueOf(VALID_AMOUNT))));
        }
    }
}