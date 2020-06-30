package org.kgsd.coffeemaker.exception;

public class ServingLimitExceededException extends Exception {
    public ServingLimitExceededException(String message) {
        super(message);
    }
}
