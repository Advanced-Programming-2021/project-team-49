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
    }

    public void removeCardFromMainDeck(CardTemplate card) {

    }

    public void addCardToSideDeck(CardTemplate card) {

    }

    public void removeCardFromSideDeck(CardTemplate card) {

    }
}