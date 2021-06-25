package model.user;

import model.cardtemplate.CardTemplate;
import model.database.Database;

import java.util.*;

public class User {
    private final String username;
    private String nickname;
    private String password;
    private int coins;
    private int score = 0;
    private final Map<CardTemplate, Integer> ownedCards = new HashMap<>();
    private final ArrayList<Deck> decks = new ArrayList<>();
    private Deck activeDeck = null;

    public User(String username, String nickname, String password, int coins) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.coins = coins;
        buildStartingDeck();
    }

    private void buildStartingDeck() {
        ownedCards.put(Database.getCardByName("Battle OX"), 1);
        ownedCards.put(Database.getCardByName("Axe Raider"), 1);
        ownedCards.put(Database.getCardByName("Horn Imp"), 1);
        ownedCards.put(Database.getCardByName("Yomi Ship"), 1);
        ownedCards.put(Database.getCardByName("Silver Fang"), 2);
        ownedCards.put(Database.getCardByName("Suijin"), 1);
        ownedCards.put(Database.getCardByName("Fireyarou"), 1);
        ownedCards.put(Database.getCardByName("Curtain of Dark Ones"), 2);
        ownedCards.put(Database.getCardByName("Feral Imp"), 1);
        ownedCards.put(Database.getCardByName("Dark Magician"), 1);
        ownedCards.put(Database.getCardByName("Wattkid"), 2);
        ownedCards.put(Database.getCardByName("Baby Dragon"), 1);
        ownedCards.put(Database.getCardByName("Hero of the East"), 2);
        ownedCards.put(Database.getCardByName("Battle Warrior"), 2);
        ownedCards.put(Database.getCardByName("Crawling Dragon"), 1);
        ownedCards.put(Database.getCardByName("Flame Manipulator"), 1);
        ownedCards.put(Database.getCardByName("Crab Turtle"), 1);
        ownedCards.put(Database.getCardByName("Haniva"), 2);
        ownedCards.put(Database.getCardByName("Bitron"), 1);
        ownedCards.put(Database.getCardByName("Leotron"), 1);
        ownedCards.put(Database.getCardByName("Alexandrite Dragon"), 1);
        ownedCards.put(Database.getCardByName("Exploder Dragon"), 1);
        ownedCards.put(Database.getCardByName("Warrior Dai Grepher"), 1);
        ownedCards.put(Database.getCardByName("Dark Blade"), 1);
        ownedCards.put(Database.getCardByName("Pot of Greed"), 1);
        ownedCards.put(Database.getCardByName("Raigeki"), 1);
        ownedCards.put(Database.getCardByName("Harpie's Feather Duster"), 1);
        ownedCards.put(Database.getCardByName("Swords of Revealing Light"), 1);
        ownedCards.put(Database.getCardByName("Messenger of Peace"), 1);
        ownedCards.put(Database.getCardByName("Yami"), 1);
        ownedCards.put(Database.getCardByName("Forest"), 1);
        ownedCards.put(Database.getCardByName("Black Pendant"), 1);
        ownedCards.put(Database.getCardByName("United We Stand"), 1);
        ownedCards.put(Database.getCardByName("Advanced Ritual Art"), 1);
        ownedCards.put(Database.getCardByName("Magic Cylinder"), 1);
        ownedCards.put(Database.getCardByName("Mirror Force"), 1);
        ownedCards.put(Database.getCardByName("Trap Hole"), 1);
        ownedCards.put(Database.getCardByName("Torrential Tribute"), 1);
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name)) {
                this.activeDeck = deck;
                break;
            }
        }
    }

    public Deck getDeckByName(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public ArrayList<Deck> getDecks() {
        decks.sort(Comparator.comparing(Deck::getName));
        return decks;
    }

    public ArrayList<CardTemplate> getOwnedCards() {
        ArrayList<CardTemplate> cards = new ArrayList<>(ownedCards.keySet());
        cards.sort(Comparator.comparing(CardTemplate::getName));
        return cards;
    }

    public void addCard(CardTemplate card) {
        if (ownedCards.containsKey(card))
            ownedCards.replace(card, ownedCards.get(card) + 1);
        else
            ownedCards.put(card, 1);
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public void deleteDeck(String name) {
        decks.removeIf(deck -> deck.getName().equals(name));
    }

    public int getCoins() {
        return coins;
    }

    public int getScore() {
        return score;
    }

    public boolean isPasswordCorrect(String password) {
        return password.equals(this.password);
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void removeScore(int score) {
        this.score -= score;
    }
}
