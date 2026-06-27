package com.coders.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> userExist(UserAlreadyExists ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> userNotfound(UserNotFound ex){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
    @ExceptionHandler(PasswordIncorrect.class)
    public ResponseEntity<String> passwordIncorrect(PasswordIncorrect ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> invalidCredentials(InvalidCredentialException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
