package model.game;

import controller.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.card.Card;

import java.util.*;

public class GameMat {

    private final Player player;
    private final Map<Location, List<Card>> locationMap;
    private final Map<Card, EffectHandler> activatedEffects = new HashMap<>();
    private final List<Limit> limits = new ArrayList<>();
    private final List<Card> monsterZone = Collections.nCopies(5, null);
    private final List<Card> spellAndTrapZone = Collections.nCopies(5, null);
    private final List<Card> graveyard = new ArrayList<>();
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> deck;
    private Card fieldZoneCard = null;
    private EffectHandler fieldZoneEffect = null;

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
            if (fieldZoneCard != null)
                moveCard(Location.FIELD_ZONE, Location.GRAVEYARD);
            fieldZoneCard = card;
        } else
            addCard(card, location, getCardCount(location));
    }

    public void removeCard(Card card, Location location) {
        List<Card> cardList = locationMap.get(location);

        activatedEffects.get(card).deActivate();
        activatedEffects.remove(card);
        cardList.remove(card);
    }

    public void removeCard(Location location, int position) {
        List<Card> cardList = locationMap.get(location);

        activatedEffects.get(cardList.get(position - 1)).deActivate();
        activatedEffects.remove(cardList.get(position - 1));
        cardList.remove(position - 1);
    }

    public void removeCard(Location location) {
        if (location == Location.FIELD_ZONE) {
            activatedEffects.get(fieldZoneCard).deActivate();
            activatedEffects.remove(fieldZoneCard);
            fieldZoneCard = null;
        } else
            removeCard(location, getCardCount(location));
    }

    public void moveCard(Location oldLocation, Location newLocation) {
        Card card = getCard(oldLocation);
        removeCard(oldLocation);
        addCard(card, newLocation);
    }

    public void moveCard(Location oldLocation, int oldPosition, Location newLocation) {
        Card card = getCard(oldLocation, oldPosition);
        removeCard(oldLocation, oldPosition);
        addCard(card, newLocation);
    }

    public void moveCard(Location oldLocation, Card card, Location newLocation) {
        removeCard(card, oldLocation);
        addCard(card, newLocation);
    }

    public void notifyEffects(Event event) {
        for (EffectHandler effect : activatedEffects.values())
            effect.notifier(event);
    }

    public void setFieldZoneEffect(EffectHandler fieldZoneEffect) {
        this.fieldZoneEffect = fieldZoneEffect;
    }

    public EffectHandler getFieldZoneEffect() {
        return fieldZoneEffect;
    }

    public List<Card> getCardList(Location location) {
        return locationMap.get(location);
    }

    public Map<Card, EffectHandler> getActivatedEffects() {
        return activatedEffects;
    }

    public List<Limit> getLimits() {
        return limits;
    }

    public void addActivatedEffect(EffectHandler effect) {
        activatedEffects.put(effect.getCard(), effect);
    }

    public void addLimit(Limit limit) {
        limits.add(limit);
    }

    public void removeLimit(Limit limit) {
        limits.remove(limit);
    }
}