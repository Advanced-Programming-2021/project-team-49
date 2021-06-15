package model.database;

import model.cardtemplate.CardTemplate;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final Map<CardTemplate, Integer> cardPrices = new HashMap<>();

    void addCard(CardTemplate card, int price) {
        cardPrices.put(card, price);
    }
}
