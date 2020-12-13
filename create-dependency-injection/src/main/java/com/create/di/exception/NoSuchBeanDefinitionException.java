package com.create.di.exception;

public class NoSuchBeanDefinitionException extends RuntimeException {

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }
}
