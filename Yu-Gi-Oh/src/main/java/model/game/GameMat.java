package model.game;

import controller.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.*;

public class GameMat {

    private final Player player;
    private final Map<Location, List<Card>> locationMap;
    private final Map<Card, EffectHandler> activatedEffects = new HashMap<>();
    private final List<Limit> limits = new ArrayList<>();
    private final List<Card> monsterZone = new ArrayList<>();
    private final List<Card> spellAndTrapZone = new ArrayList<>();
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
            addCard(card, location, getCardCount(location) + 1);
    }

    public void removeCard(Card card, Location location) {
        List<Card> cardList = locationMap.get(location);
        if (activatedEffects.containsKey(card)) {
            activatedEffects.get(card).deActivate();
            activatedEffects.remove(card);
        }
        cardList.remove(card);
    }

    public void removeCard(Location location, int position) {
        List<Card> cardList = locationMap.get(location);
        Card card = cardList.get(position - 1);

        if (location == Location.MONSTER_ZONE) {
            ((Monster) card).setAttackModifier(0);
            ((Monster) card).setDefenseModifier(0);
        }
        if (activatedEffects.containsKey(card)) {
            activatedEffects.get(card).deActivate();
            activatedEffects.remove(card);
        }
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

    public void notifyEffects(Event event, int speed) {
        for (EffectHandler effect : activatedEffects.values())
            if (effect.getSpeed() == speed)
                effect.notifier(event);
    }

    public void notifyAllEffects(Event event) {
        for (EffectHandler effect : activatedEffects.values())
            effect.notifier(event);
    }

    public void notifyFieldZoneEffect() {
        if (fieldZoneEffect != null)
            fieldZoneEffect.notifier(Event.A_MONSTER_ADDED_TO_FIELD);
    }

    public void setFieldZoneEffect(EffectHandler fieldZoneEffect) {
        this.fieldZoneEffect = fieldZoneEffect;
    }

    public List<EffectHandler> getActivatableEffects(Event event) {
        ArrayList<EffectHandler> activatableEffects = new ArrayList<>();
        for (EffectHandler value : activatedEffects.values()) {
            if (value.canBeActivated(event))
                activatableEffects.add(value);
        }
        return activatableEffects;
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