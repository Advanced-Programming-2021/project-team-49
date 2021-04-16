package model.card;

public class Card {

    private Location location;
    private boolean faceUp;
    private boolean Attacker;
    private boolean attackPossibility;
    private boolean effect;

    public Card(Location location) {
        this.location = location;
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