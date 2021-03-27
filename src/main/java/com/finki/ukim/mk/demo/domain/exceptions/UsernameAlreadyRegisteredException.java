package com.finki.ukim.mk.demo.domain.exceptions;

public class UsernameAlreadyRegisteredException extends Throwable {
    public UsernameAlreadyRegisteredException(){
        super("Username already found in our database");
    }
}
