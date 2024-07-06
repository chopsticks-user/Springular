package com.frost.springular.object.exception;

public class CustomRepeatIntervalException extends RuntimeException {
    public CustomRepeatIntervalException() {
        super("A custom repeat interval must be non-null");
    }
}
