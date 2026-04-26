package com.example.sharedkernel;

import com.example.core.entities.shared.validations.Check;
import com.example.shared.exceptions.GeneralDateTimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("validaciones básicas")
class CheckTest {

    @Test
    void requireNonNullOk() {
        String value = "test";
        assertDoesNotThrow(() -> Check.requireNonNull(value, "value"));
    }

    @Test
    void requireNonNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Check.requireNonNull(null, "value"));
    }

    @Test
    void nonEmptyOk() {
        String value = "hola";
        assertDoesNotThrow(() -> Check.requireNonEmpty(value, "value"));
    }

    @Test
    void nonEmptyThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Check.requireNonEmpty("", "value"));
    }

    @Test
    void validNumber() {
        assertTrue(Check.isValidNumber(10, 1));
    }

    @Test
    void invalidNumber() {
        assertFalse(Check.isValidNumber(0, 1));
        assertFalse(Check.isValidNumber(-5, 1));
    }

    @Test
    void emailValidAndInvalid() {
        assertDoesNotThrow(() -> Check.email("a@a.com"));
        assertThrows(IllegalArgumentException.class, () -> Check.email("bad-email"));
    }

    @Test
    void isbnValid() {
        assertTrue(Check.ISBN("0306406152"));
        assertTrue(Check.ISBN("9783161484100"));
        assertFalse(Check.ISBN("1234567890"));
        assertFalse(Check.ISBN(null));
    }

    @Test
    void stringHelpers() {
        assertTrue(Check.minStringChars("abcd", 3));
        assertFalse(Check.minStringChars("ab", 3));
        assertTrue(Check.maxStringChars("ab", 5));
        assertFalse(Check.maxStringChars(null, 5));
        assertTrue(Check.isValidString("abcd", 2, 6));
        assertFalse(Check.isValidString("a", 2, 0));
        assertTrue(Check.isNullString(null));
        assertFalse(Check.isNullString("x"));
    }

    @Test
    void dateRoundTrip() throws GeneralDateTimeException {
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        java.time.LocalDateTime dt = Check.convertStringToDateTime("01-01-2024, 00:00:00", fmt);
        String back = Check.convertDateTimeToString(dt, fmt);
        assertEquals("01-01-2024, 00:00:00", back);
    }

    @Test
    void dateInvalid() {
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
        assertThrows(GeneralDateTimeException.class, () -> Check.convertStringToDateTime("bad", fmt));
        assertThrows(GeneralDateTimeException.class, () -> Check.convertDateTimeToString(null, fmt));
    }
}