package com.example.order;

import com.example.core.entities.order.model.OrderDetail;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("orderdetail - linea de pedido")
class OrderDetailTest {

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
    @DisplayName("getdetailcost - calculo de coste")
    class DetailCost {
        @Test
        @DisplayName("el coste es (price - discount) * amount")
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
    @DisplayName("setamount - validacion")
    class SetAmount {
        @Test
        @DisplayName("setamount acepta 0 y valores positivos")
        void acceptsZeroAndPositive() throws BuildException {
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
    @DisplayName("getdetail - formato csv")
    class GetDetail {
        @Test
        @DisplayName("getdetail devuelve los 4 campos separados por coma")
        void detailContainsAllFields() {
            String d = detail.getDetail();
            assertAll(
                    () -> assertTrue(d.contains(VALID_REF)),
                    () -> assertTrue(d.contains(String.valueOf(VALID_PRICE))),
                    () -> assertTrue(d.contains(String.valueOf(VALID_AMOUNT))));
        }
    }
}