package model.user;

import model.game.Card;
import model.card.CardTemplate;

import java.util.ArrayList;

import static java.util.stream.Collectors.summingInt;

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

    public int getNumberOfOccurrences(String cardName) {
        int count = 0;
        for (Card card : mainDeck)
            if (card.getName().equals(cardName))
                count++;
        for (Card card : sideDeck)
            if (card.getName().equals(cardName))
                count++;
        return count;
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