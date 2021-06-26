package model.user;

import model.cardtemplate.CardTemplate;

import java.util.*;

public class User {
    private final String username;
    private String nickname;
    private String password;
    private int coins;
    private int score = 0;
    private final Map<CardTemplate, Integer> ownedCards = new HashMap<>();
    private final List<Deck> decks = new ArrayList<>();
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

    public List<Deck> getDecks() {
        decks.sort(Comparator.comparing(Deck::getName));
        return decks;
    }

    public List<CardTemplate> getOwnedCardsList() {
        List<CardTemplate> cards = new ArrayList<>(ownedCards.keySet());
        cards.sort(Comparator.comparing(CardTemplate::getName));
        return cards;
    }

    public Map<CardTemplate, Integer> getOwnedCardsMap() {
        return ownedCards;
    }

    public int getCardCount(CardTemplate card) {
        return ownedCards.get(card);
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
