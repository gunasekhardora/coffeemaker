package org.kgsd.coffeemaker.service;

import com.google.inject.Inject;
import org.kgsd.coffeemaker.dao.InventoryDao;
import org.kgsd.coffeemaker.exception.InventoryInsufficientException;
import org.kgsd.coffeemaker.exception.InventoryNotFoundExcpetion;
import org.kgsd.coffeemaker.exception.ServingLimitExceededException;
import org.kgsd.coffeemaker.state.StateContext;

import java.util.Map;

/**
 *  Service layer to handle all the logic
 */
public class DispenserService {
    private InventoryDao inventoryDao;
    private StateContext context;

    @Inject
    public DispenserService(InventoryDao inventoryDao, StateContext context) {
        this.inventoryDao = inventoryDao;
        this.context = context;
    }

    // A validator to return if the request can be entertained in the expected String format
    public String dispense(String beverageName, Map<String, Integer> beverage) {
        StringBuilder result = new StringBuilder();
        // Check if all ingredients are in stock
        // then dispense
        // As we are reading inputs from users sequentially, there would be no problem
        // of any race condition
        try {
            if (hasAllIngredients(beverage)) {
                if (isInLimit()) {
                    dispenseAllIngredients(beverage);
                }
            }
            result.append(beverageName)
                    .append(" is prepared");
        } catch (InventoryNotFoundExcpetion | InventoryInsufficientException | ServingLimitExceededException e) {
            result.append(beverageName)
                    .append(" cannot be prepared because ")
                    .append(e.getMessage());
        }
        return result.toString();
    }

    private boolean hasAllIngredients(Map<String, Integer> beverage) throws InventoryNotFoundExcpetion,
            InventoryInsufficientException {
        for (Map.Entry<String, Integer> ingredients : beverage.entrySet()) {
            if (!inventoryDao.exists(ingredients.getKey())) {
                throw new InventoryNotFoundExcpetion(ingredients.getKey() + " is not available");
            }
            if (inventoryDao.getQuantity(ingredients.getKey()) < ingredients.getValue()) {
                throw new InventoryInsufficientException(ingredients.getKey() + " is not sufficient");
            }
        }
        return true;
    }

    private boolean isInLimit() throws ServingLimitExceededException {
        if (context.getServedBeverages() >= context.getOutlets()) {
            throw new ServingLimitExceededException("dispensable limit " + context.getOutlets() + " reached");
        }
        context.setServedBeverages(context.getServedBeverages() + 1);
        return true;
    }

    // initialize state context
    public void setContext(Integer outlets) {
        context.setServedBeverages(0);
        context.setOutlets(outlets);
    }

    private void dispenseAllIngredients(Map<String, Integer> beverage) {
        for (Map.Entry<String, Integer> ingredients : beverage.entrySet()) {
            inventoryDao.deduct(ingredients.getKey(), ingredients.getValue());
        }
    }

    public void load(String ingredient, Integer quantity) {
        inventoryDao.add(ingredient, quantity);
    }
}
