package controller;

import exception.GameErrorException;
import model.database.Database;
import view.DeckBuilderView;
import model.card.*;
import model.user.*;

import java.util.ArrayList;
import java.util.Comparator;

public class DeckBuilderController extends AbstractController {

    private final Database database;

    public DeckBuilderController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
        title = "Deck Menu";
    }

    public void run() {
        new DeckBuilderView(this).run();
    }

    public void createDeck(String name) {
        if (user.getDeckByName(name) == null)
            throw new GameErrorException("deck with name " + name + " already exists");

        user.addDeck(new Deck(name));
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
        CardTemplate card = database.getCardByName(cardName);
        Deck deck = user.getDeckByName(deckName);

        if (card == null)
            throw new GameErrorException("card with name " + cardName + " does not exist");
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

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
        CardTemplate card = database.getCardByName(cardName);
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

    public ArrayList<Deck> getUserDecks() {
        return user.getDecks();
    }

    public ArrayList<CardTemplate> getOwnedCards() {
        return user.getOwnedCards();
    }

    public ArrayList<Monster> getMonsters(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        ArrayList<Monster> monsters = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof Monster)
                    monsters.add((Monster) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof Monster)
                    monsters.add((Monster) card);
            }
        }

        monsters.sort(Comparator.comparing(Monster::getName));
        return monsters;
    }

    public ArrayList<Spell> getSpells(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        ArrayList<Spell> spells = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof Spell)
                    spells.add((Spell) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof Spell)
                    spells.add((Spell) card);
            }
        }

        spells.sort(Comparator.comparing(Spell::getName));
        return spells;
    }

    public ArrayList<Trap> getTraps(String deckName, boolean sideDeck) {
        Deck deck = user.getDeckByName(deckName);
        if (deck == null)
            throw new GameErrorException("deck with name " + deckName + " does not exist");

        ArrayList<Trap> traps = new ArrayList<>();
        if (sideDeck) {
            for (CardTemplate card : deck.getSideDeck().keySet()) {
                if (card instanceof Trap)
                    traps.add((Trap) card);
            }
        } else {
            for (CardTemplate card : deck.getMainDeck().keySet()) {
                if (card instanceof Trap)
                    traps.add((Trap) card);
            }
        }

        traps.sort(Comparator.comparing(Trap::getName));
        return traps;
    }
}
