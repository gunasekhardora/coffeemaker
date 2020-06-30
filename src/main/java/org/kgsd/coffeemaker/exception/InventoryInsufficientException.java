package org.kgsd.coffeemaker.exception;

public class InventoryInsufficientException extends Exception {
    public InventoryInsufficientException(String message) {
        super(message);
    }
}
