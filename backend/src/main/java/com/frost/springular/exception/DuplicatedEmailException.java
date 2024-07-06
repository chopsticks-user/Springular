package com.frost.springular.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("Email address already in use");
    }
}
