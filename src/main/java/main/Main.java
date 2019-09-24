package main;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Price> oldPrices = new ArrayList<>();
        oldPrices.add(PriceExecutor.getPrice("122856", 1, 1, "01.01.2013 00:00:00", "31.01.2013 23:59:59", 11000));
        oldPrices.add(PriceExecutor.getPrice("122856", 2, 1, "10.01.2013 00:00:00", "20.01.2013 23:59:59", 99000));
        oldPrices.add(PriceExecutor.getPrice("6654", 1, 2, "01.01.2013 00:00:00", "31.01.2013 00:00:00", 5000));

        List<Price> newPrices = new ArrayList<>();
        newPrices.add(PriceExecutor.getPrice("122856", 1, 1, "20.01.2013 00:00:00", "20.02.2013 23:59:59", 11000));
        newPrices.add(PriceExecutor.getPrice("122856", 2, 1, "15.01.2013 00:00:00", "25.01.2013 23:59:59", 92000));
        newPrices.add(PriceExecutor.getPrice("6654", 1, 2, "12.01.2013 00:00:00", "13.01.2013 00:00:00", 4000));

        List<Price> resultPrices = PriceExecutor.combine(oldPrices, newPrices);
        System.out.println(resultPrices.isEmpty() ? "Collection is null :(" : resultPrices);
    }
}
