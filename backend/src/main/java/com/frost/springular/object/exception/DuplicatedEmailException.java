package com.frost.springular.object.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("Email address already in use");
    }
}
