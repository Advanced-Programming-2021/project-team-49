package model.user;

import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.cardtemplate.SpellTrapCard;
import model.game.card.Card;
import model.game.card.Monster;
import model.game.card.SpellTrap;

import java.util.*;

public class Deck {

    private String name;
    private final Map<CardTemplate, Integer> mainDeck = new HashMap<>();
    private final Map<CardTemplate, Integer> sideDeck = new HashMap<>();

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<CardTemplate, Integer> getMainDeck() {
        return mainDeck;
    }

    public void addCardToMainDeck(CardTemplate card) {
        if (mainDeck.containsKey(card))
            mainDeck.replace(card, mainDeck.get(card) + 1);
        else
            mainDeck.put(card, 1);
    }

    public boolean removeCardFromMainDeck(CardTemplate card) {
        if (mainDeck.containsKey(card)) {
            if (mainDeck.get(card) > 1)
                mainDeck.replace(card, mainDeck.get(card) - 1);
            else
                mainDeck.remove(card);
            return true;
        }
        return false;
    }

    public Map<CardTemplate, Integer> getSideDeck() {
        return sideDeck;
    }

    public void addCardToSideDeck(CardTemplate card) {
        if (sideDeck.containsKey(card))
            sideDeck.put(card, sideDeck.get(card) + 1);
        else
            sideDeck.put(card, 1);
    }

    public boolean removeCardFromSideDeck(CardTemplate card) {
        if (sideDeck.containsKey(card)) {
            if (sideDeck.containsKey(card)) {
                if (sideDeck.get(card) > 1)
                    sideDeck.replace(card, sideDeck.get(card) - 1);
                else
                    sideDeck.remove(card);
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isDeckValid() {
        if (getMainDeckSize() < 40)
            return false;
        for (CardTemplate card : mainDeck.keySet()) {
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

    public int getCardCount(CardTemplate card) {
        int cardCount = 0;
        if (mainDeck.containsKey(card))
            cardCount += mainDeck.get(card);
        if (sideDeck.containsKey(card))
            cardCount += sideDeck.get(card);
        return cardCount;
    }

    public int getMainDeckSize() {
        int deckSize = 0;
        for (CardTemplate card : mainDeck.keySet())
            deckSize += mainDeck.get(card);
        return deckSize;
    }

    public int getSideDeckSize() {
        int deckSize = 0;
        for (CardTemplate card : sideDeck.keySet())
            deckSize += sideDeck.get(card);
        return deckSize;
    }

    public List<Card> getGameDeck() {
        List<Card> gameDeck = new ArrayList<>();

        for (CardTemplate card : mainDeck.keySet())
            for (int i = 0; i < mainDeck.get(card); i++)
                if (card instanceof MonsterCard)
                    gameDeck.add(new Monster((MonsterCard) card));
                else
                    gameDeck.add(new SpellTrap((SpellTrapCard) card));

        gameDeck.sort(Comparator.comparing(Card::getName));
        return gameDeck;
    }
}