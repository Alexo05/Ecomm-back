package org.usermicroservice.exceptions;

public class UserAlreadyConsistRoleException extends RuntimeException {
    public UserAlreadyConsistRoleException(String message) {
        super(message);
    }
}
