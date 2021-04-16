package model.card;

import java.util.List;

public class Monster extends CardTemplate {
    private final int level;
    private final Attribute attribute;
    private final List<MonsterType> monsterTypes;
    private final int baseAttack;
    private final int baseDefence;

    public Monster(String name, String number, String description, int level, Attribute attribute,
                   List<MonsterType> monsterTypes, int baseAttack, int baseDefence) {
        super(name, number, description);
        this.level = level;
        this.attribute = attribute;
        this.monsterTypes = monsterTypes;
        this.baseAttack = baseAttack;
        this.baseDefence = baseDefence;
    }

    public int getLevel() {
        return level;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public List<MonsterType> getMonsterTypes() {
        return monsterTypes;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefence() {
        return baseDefence;
    }
}