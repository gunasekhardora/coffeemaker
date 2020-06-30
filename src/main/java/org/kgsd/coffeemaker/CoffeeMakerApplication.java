package org.kgsd.coffeemaker;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.kgsd.coffeemaker.commons.util.JacksonUtil;
import org.kgsd.coffeemaker.service.DispenserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.kgsd.coffeemaker.commons.Constants.*;

public class CoffeeMakerApplication {

    public static void main(String[] args) {
        System.out.println("\n-----------------------------------------");
        System.out.println("\n    A lot can happen over a coffee!!     ");
        System.out.println("\n-----------------------------------------");

        try {
            Injector injector = Guice.createInjector(new CoffeeMakerModule());
            DispenserService dispenserService = injector.getInstance(DispenserService.class);

            // Read input
            String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
            File inputFile = new File("src/main/resources/" + name);
            JsonNode masterJSONNode = JacksonUtil.getMapper().readTree(inputFile);

            // Start parsing
            JsonNode machineNode = masterJSONNode.get(MACHINE);
            Integer outlets = machineNode.get(OUTLETS).get(COUNT).asInt();

            // save context
            dispenserService.setContext(outlets);

            JsonNode inventoryNode = machineNode.get(INVENTORY);
            Iterator<Map.Entry<String, JsonNode>> inventoryIterator = inventoryNode.fields();
            while (inventoryIterator.hasNext()) {
                Map.Entry<String, JsonNode> inventoryEntry = inventoryIterator.next();
                // Load inventory
                dispenserService.load(inventoryEntry.getKey(), Integer.parseInt(inventoryEntry.getValue().toString()));
            }

            JsonNode beverageNode = machineNode.get(BEVERAGES);
            Iterator<Map.Entry<String, JsonNode>> beverageIterator = beverageNode.fields();
            while(beverageIterator.hasNext()) {
                Map.Entry<String, JsonNode> beverageEntry = beverageIterator.next();
                Map<String, Integer> beverage = JacksonUtil.getMapper().convertValue(beverageEntry.getValue(),
                        HashMap.class);
                // In an actual real world scenario, one would not be able to enter the amount of ingredients on their
                // own. Instead, we would have had a rigid static set of ingredients
                System.out.println(dispenserService.dispense(beverageEntry.getKey(), beverage));
            }
        } catch (FileNotFoundException e) {
          System.out.println("There is no such file in the input directory!");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
