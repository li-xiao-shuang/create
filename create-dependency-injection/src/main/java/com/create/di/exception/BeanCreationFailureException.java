package com.create.di.exception;

public class BeanCreationFailureException extends RuntimeException {

    public BeanCreationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
