package org.kgsd.coffeemaker.dao.impl;

import org.kgsd.coffeemaker.dao.InventoryDao;

import java.util.HashMap;
import java.util.Map;

public class FileInventoryDao implements InventoryDao {
    private Map<String, Integer> inventory = new HashMap<>();
    @Override
    public void add(String item, Integer quantity) {
        inventory.put(item, quantity);
    }

    @Override
    // An inplace update for the hashmap implementation
    public void deduct(String item, Integer quantity) {
        inventory.put(item, inventory.get(item) - quantity);
    }

    @Override
    public Integer getQuantity(String item) {
        return inventory.get(item);
    }

    @Override
    public boolean exists(String item) {
        return inventory.containsKey(item);
    }
}
