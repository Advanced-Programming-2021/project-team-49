package model.cardtemplate;

public class MonsterCard extends Card {

    private final int level;
    private final Attribute attribute;
    private final CardType cardType;
    private final MonsterType monsterType;
    private final int baseAttack;
    private final int baseDefense;

    public MonsterCard(String name, String description, Effect effect, int level, Attribute attribute,
                       CardType cardType, MonsterType monsterType, int baseAttack, int baseDefense) {
        super(name, description, effect);
        this.level = level;
        this.attribute = attribute;
        this.cardType = cardType;
        this.monsterType = monsterType;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    public int getLevel() {
        return level;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public CardType getCardType() {
        return cardType;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }
}