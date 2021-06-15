package model.user;

import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.cardtemplate.SpellTrapCard;
import model.game.card.Monster;
import model.game.card.SpellTrap;

import java.util.*;

public class Deck {

    private final String name;
    private final Map<CardTemplate, Integer> mainDeck = new HashMap<>();
    private final Map<CardTemplate, Integer> sideDeck = new HashMap<>();

    public Deck(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<CardTemplate, Integer> getMainDeck() {
        return mainDeck;
    }

    public void addCardToMainDeck(CardTemplate card) {
        mainDeck.putIfAbsent(card, 0);
        mainDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public boolean removeCardFromMainDeck(CardTemplate card) {
        if (mainDeck.containsKey(card)) {
            mainDeck.computeIfPresent(card, (key, count) -> count--);
            if (mainDeck.get(card) <= 0)
                mainDeck.remove(card);
            return true;
        }
        return false;
    }

    public Map<CardTemplate, Integer> getSideDeck() {
        return sideDeck;
    }

    public void addCardToSideDeck(CardTemplate card) {
        sideDeck.putIfAbsent(card, 0);
        sideDeck.computeIfPresent(card, (key, count) -> count++);
    }

    public boolean removeCardFromSideDeck(CardTemplate card) {
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

    public List<CardTemplate> getGameDeck() {
        List<CardTemplate> gameDeck = new ArrayList<>();

        for (CardTemplate card : mainDeck.keySet())
            for (int i = 0; i < mainDeck.get(card); i++)
                if (card instanceof MonsterCard)
                    gameDeck.add(new Monster((MonsterCard) card));
                else
                    gameDeck.add(new SpellTrap((SpellTrapCard) card));

        gameDeck.sort(Comparator.comparing(CardTemplate::getName));
        return gameDeck;
    }
}