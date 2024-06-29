package com.ing.storemanagementapi.exception;

public class ProductIdViolationException extends RuntimeException {

    public ProductIdViolationException(String message) {
        super(message);
    }
}
