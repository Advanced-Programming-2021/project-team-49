package model.game.card;

import model.cardtemplate.*;

public class Monster extends Card {

    private int attack = 0;
    private int defense = 0;
    private boolean attacker;
    private boolean effect;
    private boolean positionChanged;
    private boolean usedInAttack;
    private final MonsterCard monsterCard;

    public Monster(MonsterCard card) {
        super(card);
        this.monsterCard = card;
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

    public int getTotalAttack() {
        return Math.max(attack + getBaseAttack(), 0);
    }

    public int getTotalDefense() {
        return Math.max(defense + getBaseDefense(), 0);
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
        attack -= amount;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void increaseDefense(int amount) {
        defense += amount;
    }

    public void decreaseDefense(int amount) {
        defense -= amount;
    }
}
