package com.finki.ukim.mk.demo.domain.exceptions;

public class NoAuthenticationFoundException extends RuntimeException {
    public NoAuthenticationFoundException(){
        super("No authentication found!");
    }
}
