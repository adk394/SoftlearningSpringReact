package com.example.core.entities.shared.validations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import com.example.shared.exceptions.GeneralDateTimeException;

public final class Check {

    private Check() { }

    // regex patterns
    private static final Pattern EMAIL = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    // string validations

    public static boolean isNullString(String s) {
        return s == null;
    }

    public static boolean minStringChars(String s, int minChars) {
        return s != null && s.trim().length() >= minChars;
    }

    public static boolean maxStringChars(String s, int maxChars) {
        return s != null && s.trim().length() <= maxChars;
    }

    public static boolean isValidString(String s, int minChars, int maxChars) {
        return maxChars == 0
                ? minStringChars(s, minChars)
                : minStringChars(s, minChars) && maxStringChars(s, maxChars);
    }

    public static void requireNonEmpty(String value, String name) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    public static void requireNonNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }

    // number validations

    public static boolean isValidNumber(int value, int minValue) {
        return value >= minValue;
    }

    public static boolean isValidNumber(double value, double minValue) {
        return value >= minValue;
    }

    // isbn validation

    public static boolean ISBN(String isbn) {
        if (isbn == null) return false;

        isbn = isbn.replace("-", "").replace(" ", "");
        int length = isbn.length();

        if (length == 10) {
            int sum = 0;

            for (int i = 0; i < 9; i++) {
                char c = isbn.charAt(i);
                if (!Character.isDigit(c)) return false;
                sum += (c - '0') * (10 - i);
            }

            char lastChar = isbn.charAt(9);
            if (lastChar != 'X' && !Character.isDigit(lastChar)) return false;

            sum += (lastChar == 'X') ? 10 : (lastChar - '0');
            return sum % 11 == 0;
        }

        if (length == 13) {
            int sum = 0;

            for (int i = 0; i < 13; i++) {
                char c = isbn.charAt(i);
                if (!Character.isDigit(c)) return false;

                int digit = c - '0';
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            return sum % 10 == 0;
        }

        return false;
    }

    // email validation

    public static void email(String email) {
        requireNonEmpty(email, "email");
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    // date validations

    public static void dateNotInFuture(LocalDate date, String name) {
        requireNonNull(date, name);
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(name + " cannot be in the future");
        }
    }

    public static LocalDateTime convertStringToDateTime(
            String dateTimeString,
            DateTimeFormatter formatter) throws GeneralDateTimeException {

        if (dateTimeString == null || formatter == null) {
            throw new GeneralDateTimeException("Invalid input");
        }

        try {
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new GeneralDateTimeException("Error parsing date-time: " + e.getMessage());
        }
    }

    public static String convertDateTimeToString(
            LocalDateTime dateTime,
            DateTimeFormatter formatter) throws GeneralDateTimeException {

        if (dateTime == null || formatter == null) {
            throw new GeneralDateTimeException("Invalid input");
        }

        return dateTime.format(formatter);
    }
}
