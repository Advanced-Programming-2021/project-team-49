package model.user;

import model.card.Card;
import model.card.CardTemplate;

import java.util.ArrayList;

public class Deck {

    private final String name;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;

    Deck(String name) {
        this.name = name;
    }

    void addCardToMainDeck(CardTemplate card) {

    }

    void removeCardFromMainDeck(CardTemplate card) {

    }

    void addCardToSideDeck(CardTemplate card) {

    }

    void removeCardFromSideDeck(CardTemplate card) {

    }
}