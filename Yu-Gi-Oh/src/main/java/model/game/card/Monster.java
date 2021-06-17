package model.game.card;

import model.cardtemplate.*;

public class Monster extends MonsterCard implements Castable {

    private int attack;
    private int defense;
    private boolean faceUp;
    private boolean attacker;
    private boolean effect;
    private boolean positionChanged;
    private boolean usedInAttack;

    public Monster(MonsterCard card) {
        super(card.getName(), card.getDescription(), card.getEffect(), card.getLevel(), card.getAttribute(),
                card.getCardType(), card.getMonsterType(), card.getBaseAttack(), card.getBaseDefense());
        attack = card.getBaseAttack();
        defense = card.getBaseDefense();
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void setAttacker(boolean attacker) {
        this.attacker = attacker;
    }

    public boolean hasEffect() {
        return effect;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public boolean isPositionChanged() {
        return positionChanged;
    }

    public void setPositionChanged(boolean positionChanged) {
        this.positionChanged = positionChanged;
    }

    public boolean isUsedInAttack() {
        return usedInAttack;
    }

    public void setUsedInAttack(boolean usedInAttack) {
        this.usedInAttack = usedInAttack;
    }

    public void increaseAttack(int amount) {
        attack += amount;
    }

    public void decreaseAttack(int amount) {
        if (attack < amount)
            attack = 0;
        else
            attack -= amount;
    }

    public void increaseDefense(int amount) {
        defense += amount;
    }

    public void decreaseDefense(int amount) {
        if (defense < amount)
            defense = 0;
        else
            defense -= amount;
    }
}
