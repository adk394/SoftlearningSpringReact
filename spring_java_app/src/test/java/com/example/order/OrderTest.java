package com.example.order;

import com.example.core.entities.order.model.Order;
import com.example.core.entities.order.model.OrderDetail;
import com.example.core.entities.shared.physicals.PhysicalData;
import com.example.shared.exceptions.BuildException;
import com.example.shared.exceptions.GeneralDateTimeException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("order")
class OrderTest {

    // datos validos
    static final int VALID_ORDER_ID = 1;
    static final int VALID_CLIENT_ID = 1001;
    static final String VALID_START = "01/01/2024-10:00:00";
    static final String VALID_DESC = "Pedido de prueba";
    static final String VALID_ADDRESS = "Calle Mayor 10, Madrid";
    static final String VALID_RECEIVER = "Juan García";
    static final String VALID_PHONE = "600123456";
    static final String VALID_PAYMENT = "02/01/2024-10:00:00";

    Order order;

    @BeforeEach
    void setUp() throws BuildException, GeneralDateTimeException {
        order = Order.getInstance(VALID_ORDER_ID, VALID_CLIENT_ID,
                VALID_START, VALID_DESC);
    }


    @Nested
    @DisplayName("constructor simple")
    class GetInstanceSimple {
        @Test
        @DisplayName("crea order con datos basicos validos (herencia operation)")
        void basicFieldsAreSet() throws Exception {
            assertAll(
                    () -> assertEquals(VALID_ORDER_ID, order.getOrderID()),
                    () -> assertEquals(VALID_CLIENT_ID, order.getClientID()),
                    () -> assertEquals(VALID_DESC, order.getDescription()),
                    () -> assertNotNull(order.getShopCartList()),
                    () -> assertEquals("CREATED", order.getStatus()));
        }

        @Test
        @DisplayName("getstartdate (de operation) devuelve la fecha correcta")
        void startDateFromOperation() throws Exception {
            assertEquals(VALID_START, order.getStartDate());
        }

        @Test
        @DisplayName("id negativo lanza BuildException")
        void negativeIdThrows() {
            assertThrows(BuildException.class,
                    () -> Order.getInstance(-1, VALID_CLIENT_ID, VALID_START, VALID_DESC));
        }
    }

    @Nested
    @DisplayName("setters de ORDER")
    class OrderSetters {
        @Test
        @DisplayName("setrecieveraddress acepta direccion no vacia")
        void setAddressValid() {
            assertEquals(0, order.setRecieverAddress(VALID_ADDRESS));
            assertEquals(VALID_ADDRESS, order.getRecieverAddress());
        }

        @Test
        @DisplayName("setrecieveraddress rechaza null y vacio")
        void setAddressInvalid() {
            assertEquals(-1, order.setRecieverAddress(null));
            assertEquals(-1, order.setRecieverAddress("  "));
        }

        @Test
        @DisplayName("setrecieverperson acepta nombre no vacio")
        void setReceiverPersonValid() {
            assertEquals(0, order.setRecieverPerson(VALID_RECEIVER));
            assertEquals(VALID_RECEIVER, order.getRecieverPerson());
        }

        @Test
        @DisplayName("setrecieverperson rechaza null y vacio")
        void setReceiverPersonInvalid() {
            assertEquals(-1, order.setRecieverPerson(null));
            assertEquals(-1, order.setRecieverPerson("  "));
        }

        @Test
        @DisplayName("setphonecontacts acepta null como valido")
        void setPhoneContactsNull() {
            assertEquals(0, order.setPhoneContacts(null));
            assertTrue(order.getPhoneContacts().isEmpty());
        }

        @Test
        @DisplayName("setphonecontacts acepta varios telefonos separados por coma")
        void setPhoneContactsCommaSeparated() {
            assertEquals(0, order.setPhoneContacts("600123456, 600654321"));
            assertEquals(2, order.getPhoneContacts().size());
        }

        @Test
        @DisplayName("setphonecontacts recorta espacios en cada telefono")
        void setPhoneContactsWithSpaces() {
            assertEquals(0, order.setPhoneContacts(" 600123456 , 600654321 "));
            assertTrue(order.getPhoneContacts().contains("600123456"));
            assertTrue(order.getPhoneContacts().contains("600654321"));
        }

        @Test
        @DisplayName("setpaymentdate cambia el status a CONFIRMED")
        void paymentConfirms() {
            assertEquals(0, order.setPaymentDate(VALID_PAYMENT));
            assertEquals("CONFIRMED", order.getStatus());
        }

        @Test
        @DisplayName("setpaymentdate invalid formato devuelve -1")
        void paymentDateInvalid() {
            assertEquals(-1, order.setPaymentDate("bad-date"));
            assertEquals("CREATED", order.getStatus());
        }

        @Test
        @DisplayName("setdeliverydate cambia el status a DELIVERED")
        void deliveryConfirms() {
            order.setPaymentDate(VALID_PAYMENT);
            order.setDeliveryDate("03/01/2024-10:00:00");
            assertEquals("DELIVERED", order.getStatus());
        }

        @Test
        @DisplayName("setdeliverydate invalid formato devuelve -1")
        void deliveryDateInvalid() {
            assertEquals(-1, order.setDeliveryDate("bad-date"));
        }
    }

    @Nested
    @DisplayName("dimensiones paquete")
    class Dimensions {
        @Test
        @DisplayName("setdimensions asigna correctamente con csv valido")
        void setDimensionsValid() {
            assertEquals(0, order.setDimensions("1.5,20.0,15.0,5.0"));
            assertNotNull(order.orderPackage);
        }

        @Test
        @DisplayName("setdimensions rechaza csv con partes incorrectas")
        void setDimensionsBadFormat() {
            assertEquals(-1, order.setDimensions("1.5,20.0"));
        }

        @Test
        @DisplayName("setdimensions rechaza valores <= 0")
        void setDimensionsZero() {
            assertEquals(-1, order.setDimensions("0,0,0,0"));
        }

        @Test
        @DisplayName("setdimensions rechaza valores negativos")
        void setDimensionsNegative() {
            assertEquals(-1, order.setDimensions("-1,20.0,15.0,5.0"));
        }
    }

    @Nested
    @DisplayName("shopcart details")
    class ShopCart {
        @Test
        @DisplayName("anade orderdetails correctamente con formato csv valido")
        void addDetailsValid() {
            assertEquals(0, order.setShopCartDetails("BOOK-001,29.99,5.0,2"));
            assertEquals(1, order.getShopCartList().size());
        }

        @Test
        @DisplayName("anade multiples orderdetails separados por ;")
        void addMultipleDetails() {
            assertEquals(0, order.setShopCartDetails("B001,10.0,0.0,1;B002,20.0,2.0,3"));
            assertEquals(2, order.getShopCartList().size());
        }

        @Test
        @DisplayName("rechaza formato incorrecto (partes != 4)")
        void rejectsWrongFormat() {
            assertEquals(-1, order.setShopCartDetails("B001,10.0,0.0"));
        }

        @Test
        @DisplayName("acepta shopcart con punto y coma final")
        void addDetailsWithTrailingSemicolon() {
            assertEquals(0, order.setShopCartDetails("B001,10.0,1.0,1;"));
            assertEquals(1, order.getShopCartList().size());
        }

        @Test
        @DisplayName("rechaza shopcart con precio no numerico")
        void rejectsBadPrice() {
            assertEquals(-1, order.setShopCartDetails("B001,not-a-price,0.0,1"));
        }
    }
}