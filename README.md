# coffeemaker

A Java 11 CLI application which dispenses fixed number of beverages at one go

## To build to a fat jar

mvn assembly:assembly

Run the CoffeMakerApplication in IDE of your choice

The sample requests are to be added in `src/main/resources/` directory

`example-1.json`

```
hot_tea is prepared
hot_coffee is prepared
black_tea cannot be prepared because sugar_syrup is not sufficient
green_tea cannot be prepared because green_mixture is not available
```

`example-2.json`

```
hot_tea is prepared
hot_coffee is prepared
black_tea is prepared
green_tea is prepared
```

`example-3.json`

```
hot_tea is prepared
hot_coffee is prepared
black_tea is prepared
green_tea cannot be prepared because dispensable limit 3 reached
```
