package com.example.newdemo.exception;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Utility method for email validation
    public static boolean isValidEmail(String email) {
        // Define regex for email validation
        String EMAIL_PATTERN = "^(?!.*\\.\\.)(?!\\.)[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
        
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    // Custom Exceptions
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }
    public static class InvalidEmailException extends RuntimeException {
        public InvalidEmailException(String message) {
            super(message);
        }
    }
    

    public static class InvalidNameFormatException extends RuntimeException {
        public InvalidNameFormatException(String message) {
            super(message);
        }
    }

    public static class DataIntegrityViolationException extends RuntimeException {
        public DataIntegrityViolationException(String message) {
            super(message);
        }
    }
    public static class JwtTokenException extends RuntimeException {
        public JwtTokenException(String message) {
            super(message);
        }
    }
}
