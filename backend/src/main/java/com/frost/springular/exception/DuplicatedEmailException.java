package com.frost.springular.exception;

public class DuplicatedEmailException extends Exception {
    public DuplicatedEmailException() {
        super("Email address already in use");
    }
}
