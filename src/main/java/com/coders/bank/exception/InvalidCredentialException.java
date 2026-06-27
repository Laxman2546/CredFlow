package com.coders.bank.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String invalidEmailOrPassword) {
        super(invalidEmailOrPassword);
    }
}
