package model.game.card;

import model.cardtemplate.*;

public class Monster extends Card {

    private int attack;
    private int defense;
    private boolean attacker;
    private boolean effect;
    private boolean positionChanged;
    private boolean usedInAttack;
    private final MonsterCard monsterCard;

    public Monster(MonsterCard card) {
        super(card);
        this.monsterCard = card;
        attack = card.getBaseAttack();
        defense = card.getBaseDefense();
    }

    public int getLevel() {
        return monsterCard.getLevel();
    }

    public Attribute getAttribute() {
        return monsterCard.getAttribute();
    }

    public CardType getCardType() {
        return monsterCard.getCardType();
    }

    public MonsterType getMonsterType() {
        return monsterCard.getMonsterType();
    }

    public int getBaseAttack() {
        return monsterCard.getBaseAttack();
    }

    public int getBaseDefense() {
        return monsterCard.getBaseDefense();
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
