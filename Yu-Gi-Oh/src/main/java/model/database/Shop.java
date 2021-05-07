package model.database;

import model.card.CardTemplate;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final Map<CardTemplate, Integer> cardPrices = new HashMap<>();

    void addCard(CardTemplate card, int price) {
        cardPrices.put(card, price);
    }
}
