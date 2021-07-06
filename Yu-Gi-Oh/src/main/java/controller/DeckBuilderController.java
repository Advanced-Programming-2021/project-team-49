package controller;

import exception.GameErrorException;
import model.database.Database;
import view.DeckBuilderView;
import model.cardtemplate.*;
import model.user.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeckBuilderController extends Controller {

    public void createDeck(String name) {
        if (user.getDeckByName(name) != null)
            throw new GameErrorException("deck with name " + name + " already exists");

        user.addDeck(new Deck(name));
    }

    public void renameDeck(String newName, String oldName) {
        if (newName.equals(oldName))
            throw new GameErrorException("Enter a new name!");
        if (user.getDeckByName(newName) != null)
            throw new GameErrorException("deck with name " + newName + " already exists");

        user.getDeckByName(oldName).setName(newName);
    }

    public void deleteDeck(String name) {
        if (user.getDeckByName(name) == null)
            throw new GameErrorException("deck with name " + name + " does not exist");

        user.deleteDeck(name);
    }

    public void activateDeck(String name) {
        if (user.getDeckByName(name) == null)
            throw new GameErrorException("deck with name " + name + " does not exist");

        user.setActiveDeck(name);
    }

    public void addCardToDeck(String cardName, String deckName, boolean sideDeck) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        Deck deck = user.getDeckByName(deckName);

        if (card == null)
            throw new GameErrorException("card with name " + cardName + " does not exist");
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");
        if (deck.getCardCount(card) >= user.getCardCount(card))
            throw new GameErrorException("not enough cards");

        if (sideDeck) {
            if (deck.getSideDeckSize() > 15)
                throw new GameErrorException("side deck is full");
        } else {
            if (deck.getMainDeckSize() > 60)
                throw new GameErrorException("main deck is full");
        }

        if (deck.getCardCount(card) > 3)
            throw new GameErrorException("there are already three cards with name " + cardName
                    + " in deck " + deckName);

        if (sideDeck)
            deck.addCardToSideDeck(card);
        else
            deck.addCardToMainDeck(card);
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean sideDeck) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        Deck deck = user.getDeckByName(deckName);

        if (card == null)
            throw new GameErrorException("card with name " + cardName + " does not exist");
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        if (sideDeck) {
            if (!deck.removeCardFromSideDeck(card))
                throw new GameErrorException("card with name " + cardName + " does not exist in side deck");
        } else {
            if (!deck.removeCardFromMainDeck(card))
                throw new GameErrorException("card with name " + cardName + " does not exist in main deck");
        }
    }

    public Deck getUserActiveDeck() {
        return user.getActiveDeck();
    }

    public List<Deck> getUserDecks() {
        return user.getDecks();
    }

    public Deck getDeckByName(String name) {
        return user.getDeckByName(name);
    }

    public List<CardTemplate> getOwnedCards() {
        return user.getOwnedCardsList();
    }

    public List<MonsterCard> getMonsters(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        List<MonsterCard> monsterCards = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof MonsterCard)
                    monsterCards.add((MonsterCard) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof MonsterCard)
                    monsterCards.add((MonsterCard) card);
            }
        }

        monsterCards.sort(Comparator.comparing(MonsterCard::getName));
        return monsterCards;
    }

    public List<SpellTrapCard> getSpells(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        List<SpellTrapCard> spellCards = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof SpellTrapCard && ((SpellTrapCard) card).getType() == SpellTrapType.SPELL)
                    spellCards.add((SpellTrapCard) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof SpellTrapCard && ((SpellTrapCard) card).getType() == SpellTrapType.SPELL)
                    spellCards.add((SpellTrapCard) card);
            }
        }

        spellCards.sort(Comparator.comparing(SpellTrapCard::getName));
        return spellCards;
    }

    public List<SpellTrapCard> getTraps(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        List<SpellTrapCard> trapCards = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof SpellTrapCard && ((SpellTrapCard) card).getType() == SpellTrapType.TRAP)
                    trapCards.add((SpellTrapCard) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof SpellTrapCard && ((SpellTrapCard) card).getType() == SpellTrapType.TRAP)
                    trapCards.add((SpellTrapCard) card);
            }
        }

        trapCards.sort(Comparator.comparing(SpellTrapCard::getName));
        return trapCards;
    }
}
