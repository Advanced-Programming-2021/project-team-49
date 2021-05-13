package model.game;

import model.user.User;

import java.text.MessageFormat;
import java.util.*;

public class GameMat {

    private final Map<Location, List<Card>> locationMap;

    private final User player;
    private final Map<Integer, Card> monsterZone = new HashMap<>();
    private final Map<Integer, Card> spellAndTrapZone = new HashMap<>();
    private final List<Card> graveyard = new ArrayList<>();
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> deck;
    private Card fieldZoneCard = null;
    private int lifePoints;

    GameMat(User player, List<Card> deck, int lifePoints) {
        this.player = player;
        this.deck = deck;
        this.lifePoints = lifePoints;

        locationMap = buildLocationMap();
    }

    private Map<Location, List<Card>> buildLocationMap() {
        Map<Location, List<Card>> locationMap = new HashMap<>();

        locationMap.put(Location.DECK, deck);
        locationMap.put(Location.HAND, hand);
        locationMap.put(Location.GRAVEYARD, graveyard);

        return locationMap;
    }

    public User getPlayer() {
        return player;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public Card getCard(Location location) {
        if (location == Location.FIELD_ZONE)
            return fieldZoneCard;

        List<Card> cardList = locationMap.get(location);
        return cardList.get(cardList.size() - 1);
    }

    public Card getCard(Zone zone, int position) {
        if (zone == Zone.MONSTER_ZONE)
            return monsterZone.get(position);
        else if (zone == Zone.SPELL_AND_TRAP_ZONE)
            return spellAndTrapZone.get(position);
        return null;
    }

    public void addCard(Card card, Location location) {
        if (location == Location.FIELD_ZONE) {
            fieldZoneCard = card;
        }

        List<Card> cardList = locationMap.get(location);
        cardList.add(card);
    }

    public void addCard(Card card, Zone zone, int position) {
        if (zone == Zone.MONSTER_ZONE)
            monsterZone.put(position, card);
        else if (zone == Zone.SPELL_AND_TRAP_ZONE)
            spellAndTrapZone.put(position, card);
    }

    public void removeCard(Location location) {
        if (location == Location.FIELD_ZONE)
            fieldZoneCard = null;

        List<Card> cardList = locationMap.get(location);
        cardList.remove(cardList.size() - 1);
    }

    public void removeCard(Zone zone, int position) {
        if (zone == Zone.MONSTER_ZONE)
            monsterZone.remove(position);
        else if (zone == Zone.SPELL_AND_TRAP_ZONE)
            spellAndTrapZone.remove(position);
    }

    public void moveCard(Location oldLocation, Location newLocation) {
        Card card = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(card, newLocation);
    }

    public void moveCard(Zone oldZone, Zone newZone, int oldPosition, int newPosition) {
        Card card = getCard(oldZone, oldPosition);
        removeCard(oldZone, oldPosition);
        addCard(card, newZone, newPosition);
    }

    public void moveCard(Zone oldZone, Location newLocation, int oldPosition) {
        Card card = getCard(oldZone, oldPosition);
        removeCard(oldZone, oldPosition);
        addCard(card, newLocation);
    }

    public void moveCard(Location oldLocation, Zone newZone, int newPosition) {
        Card card = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(card, newZone, newPosition);
    }

    public void addLifePoints(int amount) {
        lifePoints += amount;
    }

    public void decreaseLifePoints(int amount) {
        lifePoints -= amount;
    }

    public String getCardStringView(Card card) {
        if (card == null)
            return "E ";
        else if (!card.isAttackPossible())
            return "O ";
        else if (card.isAttacker())
            return "OO";
        else if (card.isFaceUp())
            return "DO";
        else
            return "DH";
    }

    public String getStringView(boolean isFlipped) {
        StringBuilder handString = new StringBuilder();
        StringBuilder offsetString = new StringBuilder("\t");

        for (int i = 0; i < hand.size(); i++)
            handString.append("c \t");
        for (int i = 0; i < 5; i++)
            offsetString.append("  \t");

        if (isFlipped)
            return player.getNickname() + ":" + lifePoints
                    + "\n  \t" + handString
                    + "\n" + MessageFormat.format("{:02d}",deck.size())
                    + "\n  \t" + getCardStringView(spellAndTrapZone.get(4))
                        + "\t" + getCardStringView(spellAndTrapZone.get(2))
                        + "\t" + getCardStringView(spellAndTrapZone.get(1))
                        + "\t" + getCardStringView(spellAndTrapZone.get(3))
                        + "\t" + getCardStringView(spellAndTrapZone.get(5))
                    + "\n  \t" + getCardStringView(monsterZone.get(4)) + "\t" + getCardStringView(monsterZone.get(2))
                        + "\t" + getCardStringView(monsterZone.get(1)) + "\t" + getCardStringView(monsterZone.get(3))
                        + "\t" + getCardStringView(monsterZone.get(5))
                    + "\n" + MessageFormat.format("{:02d}",deck.size())
                    + offsetString + getCardStringView(fieldZoneCard);
        else
            return getCardStringView(fieldZoneCard) + offsetString + MessageFormat.format("{:02d}",deck.size())
                    + "\n  \t" + getCardStringView(monsterZone.get(5)) + "\t" + getCardStringView(monsterZone.get(3))
                        + "\t" + getCardStringView(monsterZone.get(1)) + "\t" + getCardStringView(monsterZone.get(2))
                        + "\t" + getCardStringView(monsterZone.get(4))
                    + "\n  \t" + getCardStringView(spellAndTrapZone.get(5))
                        + "\t" + getCardStringView(spellAndTrapZone.get(3))
                        + "\t" + getCardStringView(spellAndTrapZone.get(1))
                        + "\t" + getCardStringView(spellAndTrapZone.get(2))
                        + "\t" + getCardStringView(spellAndTrapZone.get(4))
                    + "\n" + "  " + offsetString + MessageFormat.format("{:02d}",deck.size())
                    + "\n  \t" + handString
                    + "\n" + player.getNickname() + ":" + lifePoints;
    }
}