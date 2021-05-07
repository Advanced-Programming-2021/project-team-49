package model.user;

import model.card.CardTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final String username;
    private String nickname;
    private String password;
    private int coins;
    private int score = 0;
    private final Map<CardTemplate, Integer> ownedCards = new HashMap<>();
    private ArrayList<Deck> decks;
    private Deck activeDeck;

    public User(String username, String nickname, String password, int coins) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.coins = coins;
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

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public Deck getActiveDeck() {
        return activeDeck;
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

    public void decreaseCoins(int coins) {
        this.coins -= coins;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void decreaseScore(int score) {
        this.score -= score;
    }
}
