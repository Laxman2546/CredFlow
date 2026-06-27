package com.coders.bank.exception;

public class PasswordIncorrect extends  RuntimeException{
    public PasswordIncorrect(String msg){
        super(msg);
    }
}
