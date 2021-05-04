package model.user;

import model.game.Card;
import model.card.CardTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Deck {

    private final String name;
    private Map<CardTemplate, Integer> mainDeck = new Hashtable<>();
    private Map<CardTemplate, Integer> sideDeck = new Hashtable<>();

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
}