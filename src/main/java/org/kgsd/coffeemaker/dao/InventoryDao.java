package org.kgsd.coffeemaker.dao;

/**
 *  Implementing inventory CRUD in a Dao pattern to facilitate
 *  multiple number of implementations
 */
public interface InventoryDao {
    void add(String item, Integer quantity);

    void deduct(String item, Integer quantity);

    Integer getQuantity(String item);

    boolean exists(String item);
}
