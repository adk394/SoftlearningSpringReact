package com.example.sharedkernel;

import com.example.core.entities.shared.validations.Check;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("check - validaciones basicas")
class CheckTest {

    @Test
    void requireNonNullPassesWhenValueIsNotNull() {
        String value = "test";
        assertDoesNotThrow(() -> Check.requireNonNull(value, "value"));
    }

    @Test
    void requireNonNullThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class,
                () -> Check.requireNonNull(null, "value"));
    }

    @Test
    void requireNonEmptyPassesWhenStringHasContent() {
        String value = "hola";
        assertDoesNotThrow(() -> Check.requireNonEmpty(value, "value"));
    }

    @Test
    void requireNonEmptyThrowsWhenEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> Check.requireNonEmpty("", "value"));
    }

    @Test
    void isValidNumberReturnsTrueForPositive() {
        assertTrue(Check.isValidNumber(10, 1));
    }

    @Test
    void isValidNumberReturnsFalseForZeroOrNegative() {
        assertFalse(Check.isValidNumber(0, 1));
        assertFalse(Check.isValidNumber(-5, 1));
    }
}