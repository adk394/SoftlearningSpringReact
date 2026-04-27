package com.example.client;

import com.example.core.entities.client.model.Client;
import com.example.shared.exceptions.BuildException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("client")
class ClientTest {

    // datos validos reutilizables
    static final String VALID_IDPERSON = "12345678A";
    static final String VALID_EMAIL = "test@email.com";
    static final String VALID_PHONE = "600123456";
    static final String VALID_ADDRESS = "Calle Aigua 123";
    static final String VALID_NAME = "juan perez";
    static final int VALID_IDCLIENT = 1001;
    static final String VALID_REGDATE = "01-01-2024, 00:00:00";

    Client client;

    @BeforeEach
    void setUp() throws BuildException {
        client = Client.getInstance(VALID_IDPERSON, VALID_EMAIL, VALID_PHONE,
                VALID_ADDRESS, VALID_NAME, VALID_IDCLIENT, VALID_REGDATE);
    }

    @Nested
    @DisplayName("client")
    class GetInstanceValid {
        @Test
        @DisplayName("main attributes")
        void mainAttributes() {
            assertAll(
                    () -> assertEquals(VALID_IDCLIENT, client.getIdClient()),
                    () -> assertEquals(VALID_EMAIL, client.getEmail()),
                    () -> assertEquals(VALID_NAME, client.getNamePerson()));
        }

        @Test
        @DisplayName("toString contains data")
        void toStringContains() {
            String s = client.toString();
            assertAll(
                    () -> assertTrue(s.contains(String.valueOf(VALID_IDCLIENT))),
                    () -> assertTrue(s.contains(VALID_EMAIL)),
                    () -> assertTrue(s.contains(VALID_NAME)));
        }

        @Test
        @DisplayName("datos de contacto")
        void contactDataCorrect() {
            assertEquals(VALID_IDPERSON + "|" + VALID_EMAIL + "|" + VALID_PHONE, client.getContactData());
        }
    }

    @Nested
    @DisplayName("getinstance inválido")
    class GetInstanceInvalid {
        @Test
        @DisplayName("email invalido")
        void emailInvalid() {
            assertThrows(BuildException.class,
                    () -> Client.getInstance(VALID_IDPERSON, "bad-email", VALID_PHONE,
                            VALID_ADDRESS, VALID_NAME, VALID_IDCLIENT, VALID_REGDATE));
        }

        @Test
        @DisplayName("id muy corto")
        void idPersonTooShortInstance() {
            assertThrows(BuildException.class,
                    () -> Client.getInstance("1234567", VALID_EMAIL, VALID_PHONE,
                            VALID_ADDRESS, VALID_NAME, VALID_IDCLIENT, VALID_REGDATE));
        }

        @Test
        @DisplayName("phone muy corto")
        void phoneTooShortInstance() {
            assertThrows(BuildException.class,
                    () -> Client.getInstance(VALID_IDPERSON, VALID_EMAIL, "60012345",
                            VALID_ADDRESS, VALID_NAME, VALID_IDCLIENT, VALID_REGDATE));
        }

        @Test
        @DisplayName("address muy corto")
        void addressTooShortInstance() {
            assertThrows(BuildException.class,
                    () -> Client.getInstance(VALID_IDPERSON, VALID_EMAIL, VALID_PHONE,
                            "Calle 9", VALID_NAME, VALID_IDCLIENT, VALID_REGDATE));
        }

        @Test
        @DisplayName("name muy corto")
        void nameTooShortInstance() {
            assertThrows(BuildException.class,
                    () -> Client.getInstance(VALID_IDPERSON, VALID_EMAIL, VALID_PHONE,
                            VALID_ADDRESS, "Al", VALID_IDCLIENT, VALID_REGDATE));
        }

        @Test
        @DisplayName("multiples errores")
        void multipleErrors() {
            BuildException ex = assertThrows(BuildException.class,
                    () -> Client.getInstance(VALID_IDPERSON, VALID_EMAIL, VALID_PHONE,
                            VALID_ADDRESS, VALID_NAME, 999, "bad-date"));

            String msg = ex.getMessage();
            assertTrue(msg.contains("id cliente incorrecto"));
            assertTrue(msg.contains("fecha de registro incorrecta"));
        }
    }

    @Nested
    @DisplayName("setters específicos de PERSON")
    class PersonSetters {
        @Test
        @DisplayName("idPerson null")
        void setIdPersonNull() {
            assertEquals(-1, client.setIdPerson(null));
        }

        @Test
        @DisplayName("idPerson valido")
        void setIdPersonValid() {
            assertEquals(0, client.setIdPerson("87654321B"));
            assertEquals("87654321B", client.getIdPerson());
        }

        @Test
        @DisplayName("email null")
        void setEmailNull() {
            assertEquals(-1, client.setEmail(null));
        }

        @Test
        @DisplayName("email valido")
        void setEmailValid() {
            assertEquals(0, client.setEmail("nuevo@test.com"));
            assertEquals("nuevo@test.com", client.getEmail());
        }

        @Test
        @DisplayName("email con formato inválido")
        void setEmailInvalidFormat() {
            assertEquals(-1, client.setEmail("bad-email"));
        }

        @Test
        @DisplayName("phone muy corto")
        void setPhoneTooShort() {
            assertEquals(-1, client.setPhone("12345"));
        }

        @Test
        @DisplayName("phone valido")
        void setPhoneValid() {
            assertEquals(0, client.setPhone("600123457"));
            assertEquals("600123457", client.getPhone());
        }

        @Test
        @DisplayName("phone con espacios en blanco rechaza")
        void setPhoneWhitespaceInvalid() {
            assertEquals(-1, client.setPhone("   "));
        }

        @Test
        @DisplayName("address con espacios en blanco")
        void setAddressWhitespace() {
            assertEquals(-1, client.setAdress("   "));
        }

        @Test
        @DisplayName("address valida")
        void setAddressValid() {
            assertEquals(0, client.setAdress("Calle Nueva 45"));
            assertEquals("Calle Nueva 45", client.getAdress());
        }

        @Test
        @DisplayName("name muy corto")
        void setNameTooShort() {
            assertEquals(-1, client.setNamePerson("Al"));
        }

        @Test
        @DisplayName("name valido")
        void setNameValid() {
            assertEquals(0, client.setNamePerson("Maria Lopez"));
            assertEquals("Maria Lopez", client.getNamePerson());
        }

        @Test
        @DisplayName("registration date format")
        void registrationDateFormat() {
            assertTrue(client.getRegistrationDate().contains("01-01-2024"));
        }
    }

    @Nested
    @DisplayName("setters específicos de CLIENT")
    class ClientSetters {
        @Test
        @DisplayName("setIdClient acepta id >= 1000")
        void setIdClientValid() {
            assertEquals(0, client.setIdClient(1500));
            assertEquals(1500, client.getIdClient());
        }

        @Test
        @DisplayName("setIdClient rechaza id < 1000")
        void setIdClientInvalid() {
            assertEquals(-1, client.setIdClient(1));
        }

        @Test
        @DisplayName("setIdClient acepta 1000 como limite")
        void setIdClientBoundaryValid() {
            assertEquals(0, client.setIdClient(1000));
            assertEquals(1000, client.getIdClient());
        }

        @Test
        @DisplayName("setRegistrationDate acepta fecha formateada")
        void setRegistrationDateOk() {
            assertEquals(0, client.setRegistrationDate("02-02-2024, 12:00:00"));
            assertTrue(client.getRegistrationDate().contains("02-02-2024"));
        }

        @Test
        @DisplayName("setRegistrationDate rechaza formato invalido")
        void setRegistrationDateBad() {
            assertEquals(-1, client.setRegistrationDate("invalid-date"));
        }

        @Test
        @DisplayName("setRegistrationDate rechaza null")
        void setRegistrationDateNull() {
            assertEquals(-1, client.setRegistrationDate(null));
        }
    }
}