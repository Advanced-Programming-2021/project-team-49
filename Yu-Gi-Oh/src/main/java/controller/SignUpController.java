package controller;

import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.database.Userbase;
import model.user.Deck;
import model.user.User;

import java.util.Map;

public class SignUpController extends Controller {

    public final Userbase userbase = DATABASE.getUserbase();

    public void createUser(String username, String nickname,
            String password, String confirmPassword,
            String profilePicPath) {
        checkEmptyFields(username, password, confirmPassword);
        if (userbase.getUserByUsername(username) != null)
            throw new GameErrorException("User with username " + username + " already exists");
        if (userbase.getUserByNickname(username) != null)
            throw new GameErrorException("User with nickname " + nickname + " already exists");
        if (!password.equals(confirmPassword))
            throw new GameErrorException("Passwords didn't match");

        userbase.addUser(username, nickname, password, profilePicPath);
        buildStartingDeck(userbase.getUserByUsername(username));
    }

    private void checkEmptyFields(String username, String password, String confirmPassword) {
        if (username.length() == 0)
            throw new GameErrorException("Enter username");
        if (password.length() == 0)
            throw new GameErrorException("Enter password");
        if (confirmPassword.length() == 0)
            throw new GameErrorException("Confirm password");
    }

    private void buildStartingDeck(User user) {
        Map<CardTemplate, Integer> ownedCards = user.getOwnedCardsMap();

        ownedCards.put(DATABASE.getCardByName("Battle OX"), 1);
        ownedCards.put(DATABASE.getCardByName("Axe Raider"), 1);
        ownedCards.put(DATABASE.getCardByName("Horn Imp"), 1);
        ownedCards.put(DATABASE.getCardByName("Yomi Ship"), 1);
        ownedCards.put(DATABASE.getCardByName("Silver Fang"), 2);
        ownedCards.put(DATABASE.getCardByName("Suijin"), 1);
        ownedCards.put(DATABASE.getCardByName("Fireyarou"), 1);
        ownedCards.put(DATABASE.getCardByName("Curtain of the dark ones"), 2);
        ownedCards.put(DATABASE.getCardByName("Feral Imp"), 1);
        ownedCards.put(DATABASE.getCardByName("Dark Magician"), 1);
        ownedCards.put(DATABASE.getCardByName("Wattkid"), 2);
        ownedCards.put(DATABASE.getCardByName("Baby Dragon"), 1);
        ownedCards.put(DATABASE.getCardByName("Hero of the East"), 2);
        ownedCards.put(DATABASE.getCardByName("Battle Warrior"), 2);
        ownedCards.put(DATABASE.getCardByName("Crawling Dragon"), 1);
        ownedCards.put(DATABASE.getCardByName("Flame Manipulator"), 1);
        ownedCards.put(DATABASE.getCardByName("Crab Turtle"), 1);
        ownedCards.put(DATABASE.getCardByName("Haniwa"), 2);
        ownedCards.put(DATABASE.getCardByName("Bitron"), 1);
        ownedCards.put(DATABASE.getCardByName("Leotron"), 1);
        ownedCards.put(DATABASE.getCardByName("Alexandrite Dragon"), 1);
        ownedCards.put(DATABASE.getCardByName("Exploder Dragon"), 1);
        ownedCards.put(DATABASE.getCardByName("Warrior Dai Grepher"), 1);
        ownedCards.put(DATABASE.getCardByName("Dark Blade"), 1);
        ownedCards.put(DATABASE.getCardByName("Pot of Greed"), 1);
        ownedCards.put(DATABASE.getCardByName("Raigeki"), 1);
        ownedCards.put(DATABASE.getCardByName("Harpie's Feather Duster"), 1);
        ownedCards.put(DATABASE.getCardByName("Swords of Revealing Light"), 1);
        ownedCards.put(DATABASE.getCardByName("Messenger of Peace"), 1);
        ownedCards.put(DATABASE.getCardByName("Yami"), 1);
        ownedCards.put(DATABASE.getCardByName("Forest"), 1);
        ownedCards.put(DATABASE.getCardByName("Black Pendant"), 1);
        ownedCards.put(DATABASE.getCardByName("United We Stand"), 1);
        ownedCards.put(DATABASE.getCardByName("Advanced Ritual Art"), 1);
        ownedCards.put(DATABASE.getCardByName("Magic Cylinder"), 1);
        ownedCards.put(DATABASE.getCardByName("Mirror Force"), 1);
        ownedCards.put(DATABASE.getCardByName("Trap Hole"), 1);
        ownedCards.put(DATABASE.getCardByName("Torrential Tribute"), 1);

        Deck startingDeck;
        if (user.getNickname().equals(""))
            startingDeck = new Deck("Starting Deck");
        else
            startingDeck = new Deck(user.getNickname());
        for (CardTemplate card : ownedCards.keySet()) {
            for (int i = 0; i < ownedCards.get(card); i++)
                startingDeck.addCardToMainDeck(card);
        }

        user.addDeck(startingDeck);
        user.setActiveDeck(user.getNickname());
    }
}
