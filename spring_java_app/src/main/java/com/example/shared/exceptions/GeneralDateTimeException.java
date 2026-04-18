package com.example.shared.exceptions;

public class GeneralDateTimeException extends Exception {
    public GeneralDateTimeException(String message) { super(message); }
    public GeneralDateTimeException(String message, Throwable cause) { super(message, cause); }
}
