package controller;

import model.user.Deck;
import model.user.User;

import java.util.Map;

public class DeckBuilderController extends AbstractController {
    public static final String TITLE = "Deck Menu";
    private final MasterController masterController;

    public DeckBuilderController(MasterController masterController, User user) {
        this.masterController = masterController;
    }

    public void run() {

    }

    public void buildDeck(String name) {

    }

    public void deleteDeck(String name) {

    }

    public void addCardToDeck(String cardName, String deckName) {

    }

    public void removeCardFromDeck(String cardName, String deckName) {

    }
}
