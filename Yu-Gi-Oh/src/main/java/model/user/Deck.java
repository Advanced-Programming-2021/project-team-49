package model.user;

import model.cardtemplate.Card;
import model.cardtemplate.MonsterCard;
import model.cardtemplate.SpellTrapCard;
import model.game.card.Monster;
import model.game.card.SpellTrap;

import java.util.*;

public class Deck {

    private final String name;
    private final Map<Card, Integer> mainDeck = new HashMap<>();
    private final Map<Card, Integer> sideDeck = new HashMap<>();

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Card, Integer> getMainDeck() {
        return mainDeck;
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.putIfAbsent(card, 0);
        mainDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public boolean removeCardFromMainDeck(Card card) {
        if (mainDeck.containsKey(card)) {
            mainDeck.computeIfPresent(card, (key, count) -> count--);
            if (mainDeck.get(card) <= 0)
                mainDeck.remove(card);
            return true;
        }
        return false;
    }

    public Map<Card, Integer> getSideDeck() {
        return sideDeck;
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.putIfAbsent(card, 0);
        sideDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public boolean removeCardFromSideDeck(Card card) {
        if (sideDeck.containsKey(card)) {
            sideDeck.computeIfPresent(card, (key, count) -> count--);
            if (sideDeck.get(card) <= 0)
                sideDeck.remove(card);
            return true;
        }
        return false;
    }

    public boolean isDeckValid() {
        if (getMainDeckSize() < 40)
            return false;
        for (Card card : mainDeck.keySet()) {
            if (mainDeck.get(card) > 3)
                return false;
            if (sideDeck.containsKey(card))
                if (sideDeck.get(card) > 3)
                    return false;
                if (getCardCount(card) > 3)
                    return false;
        }
        return true;
    }

    public int getCardCount(Card card) {
        int cardCount = 0;
        if (mainDeck.containsKey(card))
            cardCount += mainDeck.get(card);
        if (sideDeck.containsKey(card))
            cardCount += sideDeck.get(card);
        return cardCount;
    }

    public int getMainDeckSize() {
        int deckSize = 0;
        for (Card card : mainDeck.keySet())
            deckSize += mainDeck.get(card);
        return deckSize;
    }

    public int getSideDeckSize() {
        int deckSize = 0;
        for (Card card : sideDeck.keySet())
            deckSize += sideDeck.get(card);
        return deckSize;
    }

    public List<Card> getGameDeck() {
        List<Card> gameDeck = new ArrayList<>();

        for (Card card : mainDeck.keySet())
            for (int i = 0; i < mainDeck.get(card); i++)
                if (card instanceof MonsterCard)
                    gameDeck.add(new Monster((MonsterCard) card));
                else
                    gameDeck.add(new SpellTrap((SpellTrapCard) card));

        gameDeck.sort(Comparator.comparing(Card::getName));
        return gameDeck;
    }
}