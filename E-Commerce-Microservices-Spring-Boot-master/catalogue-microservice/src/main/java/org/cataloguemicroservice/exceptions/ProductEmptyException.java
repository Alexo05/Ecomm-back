package org.cataloguemicroservice.exceptions;

public class ProductEmptyException extends RuntimeException {
    public ProductEmptyException(String message) {
        super(message);
    }
}
