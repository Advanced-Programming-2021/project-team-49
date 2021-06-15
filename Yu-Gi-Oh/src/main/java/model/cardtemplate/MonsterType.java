package model.cardtemplate;

public enum MonsterType {
    WARRIOR("Warrior"),
    DRAGON("Dragon"),
    SPELLCASTER("Spellcaster"),
    CYBERSE("Cyberse"),
    AQUA("Aqua"),
    BEAST_WARRIOR("Beast-Warrior"),
    FIEND("Fiend"),
    THUNDER("Thunder"),
    MACHINE("Machine"),
    BEAST("Beast"),
    PYRO("Pyro"),
    ROCK("Rock"),
    INSECT("Insect"),
    FAIRY("Fairy"),
    SEA_SERPENT("Sea Serpent");

    private final String monsterType;

    MonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public String getMonsterType() {
        return monsterType;
    }
}