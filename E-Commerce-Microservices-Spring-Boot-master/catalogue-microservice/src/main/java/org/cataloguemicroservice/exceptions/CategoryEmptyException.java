package org.cataloguemicroservice.exceptions;

public class CategoryEmptyException extends RuntimeException {
    public CategoryEmptyException(String message) {
        super(message);
    }
}
