package model.database;

import model.cardtemplate.Card;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final Map<Card, Integer> cardPrices = new HashMap<>();

    void addCard(Card card, int price) {
        cardPrices.put(card, price);
    }
}
