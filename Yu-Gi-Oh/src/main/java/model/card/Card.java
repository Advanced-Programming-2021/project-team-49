package model.card;

public class Card {

    private Location location;
    private boolean faceUp;
    private boolean Attacker;
    private boolean attackPossibility;
    private Effect effect;

    public Card(Location location, Effect effect) {
        this.location = location;
        this.effect = effect;
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

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}