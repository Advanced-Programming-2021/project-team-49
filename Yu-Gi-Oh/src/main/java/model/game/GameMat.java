package model.game;

import controller.EffectController;
import controller.effects.Limit;
import model.cardtemplate.CardTemplate;
import model.game.card.Card;

import java.util.*;

public class GameMat {

    private final Player player;
    private final Map<Location, List<Card>> locationMap;
    private final List<Card> monsterZone = Collections.nCopies(5, null);
    private final List<Card> spellAndTrapZone = Collections.nCopies(5, null);
    private final List<Card> graveyard = new ArrayList<>();
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> deck;
    private Card fieldZoneCard = null;
    private final List<EffectController> activatedEffects = new ArrayList<>();
    private final List<Limit> limits = new ArrayList<>();

    public GameMat(Player player, List<Card> deck) {
        this.player = player;
        this.deck = deck;

        Collections.shuffle(deck);
        locationMap = buildLocationMap();
    }

    private Map<Location, List<Card>> buildLocationMap() {
        Map<Location, List<Card>> locationMap = new HashMap<>();
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

    public Card getCard(Location location, int position) {
        if (location == Location.FIELD_ZONE)
            return fieldZoneCard;

        List<Card> cardList = locationMap.get(location);
        return cardList.get(position - 1);
    }

    public Card getCard(Location location) {
        if (location == Location.FIELD_ZONE)
            return fieldZoneCard;

        return getCard(location, getCardCount(location));
    }

    public void addCard(Card card, Location location, int position) {
        List<Card> cardList = locationMap.get(location);
        cardList.add(position - 1, card);
    }

    public void addCard(Card card, Location location) {
        if (location == Location.FIELD_ZONE) {
            fieldZoneCard = card;
        }

        addCard(card, location, getCardCount(location));
    }

    public void removeCard(Card card, Location location) {
        List<Card> cardList = locationMap.get(location);
        cardList.remove(card);
    }

    public void removeCard(Location location, int position) {
        List<Card> cardList = locationMap.get(location);
        cardList.remove(position - 1);
    }

    public void removeCard(Location location) {
        if (location == Location.FIELD_ZONE)
            fieldZoneCard = null;

        removeCard(location, getCardCount(location));
    }

    public void moveCard(Location oldLocation, int oldPosition, Location newLocation, int newPosition) {
        Card card = getCard(oldLocation, oldPosition);
        removeCard(oldLocation, oldPosition);
        addCard(card, newLocation, newPosition);
    }

    public void moveCard(Location oldLocation, Location newLocation) {
        Card card = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(card, newLocation);
    }

    public void moveCard(Location oldLocation, Location newLocation, int newPosition) {
        Card card = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(card, newLocation, newPosition);
    }

    public void moveCard(Location oldLocation, int oldPosition, Location newLocation) {
        Card card = getCard(oldLocation, oldPosition);
        removeCard(oldLocation, oldPosition);
        addCard(card, newLocation);
    }

    public List<Card> getCardList(Location location) {
        return locationMap.get(location);
    }

    public List<EffectController> getActivatedEffects() {
        return activatedEffects;
    }

    public List<Limit> getLimits() {
        return limits;
    }

    public void addActivatedEffect(EffectController effect) {
        activatedEffects.add(effect);
    }

    public void removeActivatedEffect(EffectController effect) {
        activatedEffects.remove(effect);
    }

    public void addLimit(Limit limit) {
        limits.add(limit);
    }

    public void removeLimit(Limit limit) {
        limits.remove(limit);
    }
}