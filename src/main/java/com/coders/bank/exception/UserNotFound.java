package com.coders.bank.exception;

public class UserNotFound extends  RuntimeException{
   public UserNotFound(String msg){
        super(msg);
    }
}
