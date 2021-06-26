package controller;

import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.database.Database;
import model.database.Userbase;
import model.user.Deck;
import model.user.User;
import view.LoginView;

import java.util.List;
import java.util.Map;

public class LoginController extends AbstractController {

    public final Userbase userbase;

    public LoginController(MasterController masterController, Userbase userbase) {
        super(masterController, null);
        this.userbase = userbase;
        title = "Login Menu";
    }

    public void run() {
        new LoginView(this).run();
    }

    public void createUser(String username, String nickname, String password) {
        if (userbase.getUserByUsername(username) != null)
            throw new GameErrorException("user with username " + username + " already exists");
        else if (userbase.getUserByNickname(username) != null)
            throw new GameErrorException("user with nickname " + nickname + " already exists");

        userbase.addUser(username, nickname, password);
        buildStartingDeck(masterController.getDatabase(), userbase.getUserByUsername(username));
    }

    public void login(String username, String password) {
        User user = userbase.getUserByUsername(username);
        if (user == null || !user.isPasswordCorrect(password))
            throw new GameErrorException("Username and password didn't match!");

        MainMenuController mainMenuController = new MainMenuController(masterController,
                user, masterController.getDatabase());
        masterController.setNextController(mainMenuController);
    }

    private void buildStartingDeck(Database database, User user) {
        Map<CardTemplate, Integer> ownedCards = user.getOwnedCardsMap();

        ownedCards.put(database.getCardByName("Battle OX"), 1);
        ownedCards.put(database.getCardByName("Axe Raider"), 1);
        ownedCards.put(database.getCardByName("Horn Imp"), 1);
        ownedCards.put(database.getCardByName("Yomi Ship"), 1);
        ownedCards.put(database.getCardByName("Silver Fang"), 2);
        ownedCards.put(database.getCardByName("Suijin"), 1);
        ownedCards.put(database.getCardByName("Fireyarou"), 1);
        ownedCards.put(database.getCardByName("Curtain of the dark ones"), 2);
        ownedCards.put(database.getCardByName("Feral Imp"), 1);
        ownedCards.put(database.getCardByName("Dark Magician"), 1);
        ownedCards.put(database.getCardByName("Wattkid"), 2);
        ownedCards.put(database.getCardByName("Baby Dragon"), 1);
        ownedCards.put(database.getCardByName("Hero of the East"), 2);
        ownedCards.put(database.getCardByName("Battle Warrior"), 2);
        ownedCards.put(database.getCardByName("Crawling Dragon"), 1);
        ownedCards.put(database.getCardByName("Flame Manipulator"), 1);
        ownedCards.put(database.getCardByName("Crab Turtle"), 1);
        ownedCards.put(database.getCardByName("Haniwa"), 2);
        ownedCards.put(database.getCardByName("Bitron"), 1);
        ownedCards.put(database.getCardByName("Leotron"), 1);
        ownedCards.put(database.getCardByName("Alexandrite Dragon"), 1);
        ownedCards.put(database.getCardByName("Exploder Dragon"), 1);
        ownedCards.put(database.getCardByName("Warrior Dai Grepher"), 1);
        ownedCards.put(database.getCardByName("Dark Blade"), 1);
        ownedCards.put(database.getCardByName("Pot of Greed"), 1);
        ownedCards.put(database.getCardByName("Raigeki"), 1);
        ownedCards.put(database.getCardByName("Harpie's Feather Duster"), 1);
        ownedCards.put(database.getCardByName("Swords of Revealing Light"), 1);
        ownedCards.put(database.getCardByName("Messenger of Peace"), 1);
        ownedCards.put(database.getCardByName("Yami"), 1);
        ownedCards.put(database.getCardByName("Forest"), 1);
        ownedCards.put(database.getCardByName("Black Pendant"), 1);
        ownedCards.put(database.getCardByName("United We Stand"), 1);
        ownedCards.put(database.getCardByName("Advanced Ritual Art"), 1);
        ownedCards.put(database.getCardByName("Magic Cylinder"), 1);
        ownedCards.put(database.getCardByName("Mirror Force"), 1);
        ownedCards.put(database.getCardByName("Trap Hole"), 1);
        ownedCards.put(database.getCardByName("Torrential Tribute"), 1);

        Deck startingDeck = new Deck(user.getNickname());
        for (CardTemplate card : ownedCards.keySet()) {
            for (int i = 0; i < ownedCards.get(card); i++)
                startingDeck.addCardToMainDeck(card);
        }

        user.addDeck(startingDeck);
        user.setActiveDeck(user.getNickname());
    }

    @Override
    public void escape() {
        masterController.setNextController(null);
    }
}
