package model.game;

import model.card.CardTemplate;

public class Card {
    private CardTemplate cardTemplate;
    private Location location;
    private boolean faceUp;
    private boolean Attacker;
    private boolean attackPossibility;
    private boolean effect;

    public Card(CardTemplate cardTemplate, Location location) {
        this.cardTemplate = cardTemplate;
        this.location = location;
    }

    public String getName() {
        return cardTemplate.getName();
    }

    public String getDescription() {
        return cardTemplate.getDescription();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setAttacker(boolean attacker) {
        Attacker = attacker;
    }

    public boolean isAttacker() {
        return Attacker;
    }

    public void setAttackPossibility(boolean attackPossibility) {
        this.attackPossibility = attackPossibility;
    }

    public boolean isAttackPossible() {
        return attackPossibility;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public boolean hasEffect() {
        return effect;
    }
}