package model.user;

import model.card.CardTemplate;

import java.util.HashMap;
import java.util.Map;

public class Deck {

    private final String name;
    private final Map<CardTemplate, Integer> mainDeck = new HashMap<>();
    private final Map<CardTemplate, Integer> sideDeck = new HashMap<>();

    Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCardToMainDeck(CardTemplate card) {
        mainDeck.putIfAbsent(card, 0);
        mainDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public void removeCardFromMainDeck(CardTemplate card) {
        mainDeck.computeIfPresent(card, (key, count) -> count--);
        if (mainDeck.get(card) <= 0)
            mainDeck.remove(card);
    }

    public void addCardToSideDeck(CardTemplate card) {
        sideDeck.putIfAbsent(card, 0);
        sideDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public void removeCardFromSideDeck(CardTemplate card) {
        sideDeck.computeIfPresent(card, (key, count) -> count--);
        if (sideDeck.get(card) <= 0)
            sideDeck.remove(card);
    }

    public boolean isDeckValid() {
        for (CardTemplate cardTemplate : mainDeck.keySet()) {
            if (mainDeck.get(cardTemplate) > 3)
                return false;
            if (sideDeck.containsKey(cardTemplate))
                if (mainDeck.get(cardTemplate) + sideDeck.get(cardTemplate) > 3)
                    return false;
        }
        return true;
    }
}