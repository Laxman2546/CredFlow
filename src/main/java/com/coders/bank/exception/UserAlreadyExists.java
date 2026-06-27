package com.coders.bank.exception;

public class UserAlreadyExists  extends RuntimeException {
   public  UserAlreadyExists(String msg){
        super(msg);
    }
}
