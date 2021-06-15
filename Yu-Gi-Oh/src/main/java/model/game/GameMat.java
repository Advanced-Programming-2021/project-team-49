package model.game;

import model.cardtemplate.CardTemplate;

import java.util.*;

public class GameMat {

    private final Player player;
    private final Map<Location, List<CardTemplate>> locationMap;
    private final List<CardTemplate> monsterZone = Collections.nCopies(5, null);
    private final List<CardTemplate> spellAndTrapZone = Collections.nCopies(5, null);
    private final List<CardTemplate> graveyard = new ArrayList<>();
    private final List<CardTemplate> hand = new ArrayList<>();
    private final List<CardTemplate> deck;
    private CardTemplate fieldZoneCardTemplate = null;

    public GameMat(Player player, List<CardTemplate> deck) {
        this.player = player;
        this.deck = deck;

        Collections.shuffle(deck);
        locationMap = buildLocationMap();
    }

    private Map<Location, List<CardTemplate>> buildLocationMap() {
        Map<Location, List<CardTemplate>> locationMap = new HashMap<>();
        locationMap.put(Location.DECK, deck);
        locationMap.put(Location.HAND, hand);
        locationMap.put(Location.GRAVEYARD, graveyard);
        locationMap.put(Location.MONSTER_ZONE, monsterZone);
        locationMap.put(Location.SPELL_AND_TRAP_ZONE, spellAndTrapZone);

        return locationMap;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCardCount(Location location) {
        return locationMap.get(location).size();
    }

    public CardTemplate getCard(Location location, int position) {
        if (location == Location.FIELD_ZONE)
            return fieldZoneCardTemplate;

        List<CardTemplate> cardTemplateList = locationMap.get(location);
        return cardTemplateList.get(position - 1);
    }

    public CardTemplate getCard(Location location) {
        if (location == Location.FIELD_ZONE)
            return fieldZoneCardTemplate;

        return getCard(location, getCardCount(location));
    }

    public void addCard(CardTemplate cardTemplate, Location location, int position) {
        List<CardTemplate> cardTemplateList = locationMap.get(location);
        cardTemplateList.add(position - 1, cardTemplate);
    }

    public void addCard(CardTemplate cardTemplate, Location location) {
        if (location == Location.FIELD_ZONE) {
            fieldZoneCardTemplate = cardTemplate;
        }

        addCard(cardTemplate, location, getCardCount(location));
    }

    public void removeCard(CardTemplate cardTemplate, Location location) {
        List<CardTemplate> cardTemplateList = locationMap.get(location);
        cardTemplateList.remove(cardTemplate);
    }

    public void removeCard(Location location, int position) {
        List<CardTemplate> cardTemplateList = locationMap.get(location);
        cardTemplateList.remove(position - 1);
    }

    public void removeCard(Location location) {
        if (location == Location.FIELD_ZONE)
            fieldZoneCardTemplate = null;

        removeCard(location, getCardCount(location));
    }

    public void moveCard(Location oldLocation, int oldPosition, Location newLocation, int newPosition) {
        CardTemplate cardTemplate = getCard(oldLocation, oldPosition);
        removeCard(oldLocation, oldPosition);
        addCard(cardTemplate, newLocation, newPosition);
    }

    public void moveCard(Location oldLocation, Location newLocation) {
        CardTemplate cardTemplate = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(cardTemplate, newLocation);
    }

    public void moveCard(Location oldLocation, Location newLocation, int newPosition) {
        CardTemplate cardTemplate = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(cardTemplate, newLocation, newPosition);
    }

    public void moveCard(Location oldLocation, int oldPosition, Location newLocation) {
        CardTemplate cardTemplate = getCard(oldLocation, oldPosition);
        removeCard(oldLocation, oldPosition);
        addCard(cardTemplate, newLocation);
    }

    public List<CardTemplate> getCardList(Location location) {
        return locationMap.get(location);
    }
}