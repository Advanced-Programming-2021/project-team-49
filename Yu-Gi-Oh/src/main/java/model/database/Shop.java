package model.database;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final Map<String, Integer> cardPrices = new HashMap<>();
    private int revenue = 0;

    public int getRevenue() {
        return revenue;
    }

    public void addRevenue(int amount) {
        revenue += amount;
    }

    void addCard(String name, int price) {
        cardPrices.put(name, price);
    }
}
