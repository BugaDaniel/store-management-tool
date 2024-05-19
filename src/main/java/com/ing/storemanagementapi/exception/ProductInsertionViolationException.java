package com.ing.storemanagementapi.exception;

public class ProductInsertionViolationException extends RuntimeException {

    public ProductInsertionViolationException(String message) {
        super(message);
    }
}
