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