package com.frost.springular.exception;

public class CustomRepeatIntervalException extends RuntimeException {
    public CustomRepeatIntervalException() {
        super("A custom repeat interval must be non-null");
    }
}
