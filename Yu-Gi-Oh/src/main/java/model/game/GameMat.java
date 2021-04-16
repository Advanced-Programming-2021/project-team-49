package model.game;

import model.card.Card;
import model.card.Location;

import java.util.ArrayList;
import java.util.Map;

public class GameMat {

    private Map<Integer, Card> monsterZone;
    private Map<Integer, Card> spellAndTrapZone;
    private ArrayList<Card> destroyedCards;
    private ArrayList<Card> graveyard;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private Card fieldZone;

    GameMat(ArrayList<Card> deck) {
        this.deck = deck;
    }

    void addCard(Card card, Location location) {

    }

    void removeCard(Card card, Location location) {

    }
}