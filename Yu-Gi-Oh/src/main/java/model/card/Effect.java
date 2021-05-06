package model.card;

public enum Effect {
    NONE("None"),

    // Monster
    YOMI_SHIP("Yomi Ship"),
    SUIJIN("Suijin"),
    MAN_EATER_BUG("Man-Eater Bug"),
    GATE_GUARDIAN("Gate Guardian"),
    SCANNER("Scanner"),
    MARSHMALLON("Marshmallon"),
    BEAST_KING_BARBAROS("Beast King Barbaros"),
    TEX_CHANGER("Tex Changer"),
    THE_CALCULATOR("The Calculator"),
    MIRAGE_DRAGON("Mirage Dragon"),
    HERALD_OF_CREATION("Herald of Creation"),
    EXPLODER_DRAGON("Exploder Dragon"),
    TERRATIGER("Terratiger, the Empowered Warrior"),
    THE_TRICKY("The Tricky"),
    COMMAND_KNIGHT("Command Knight"),

    // Trap
    TRAP_HOLE("Trap Hole"),
    MIRROR_FORCE("Mirror Force"),
    MAGIC_CYLINDER("Magic Cylinder"),
    MIND_CRUSH("Mind Crush"),
    TORRENTIAL_TRIBUTE("Torrential Tribute"),
    TIME_SEAL("Time Seal"),
    NEGATE_ATTACK("Negate Attack"),
    SOLEMN_WARNING("Solemn Warning"),
    MAGIC_JAMMER("Magic Jammer"),
    CALL_OF_THE_HAUNTED("Call of The Haunted"),
    VANITYS_EMPTINESS("Vanity's Emptiness"),
    WALL_OF_REVEALING_LIGHT("Wall of Revealing Light"),

    // Spell
    MONSTER_REBORN("Monster Reborn"),
    TERRAFORMING("Terraforming"),
    POT_OF_GREED("Pot of Greed"),
    RAIGEKI("Raigeki"),
    CHANGE_OF_HEART("Change of Heart"),
    SWORDS_OF_REVEALING_LIGHT("Swords of Revealing Light"),
    HARPIES_FEATHER_DUSTER("Harpie's Feather Duster"),
    DARK_HOLE("Dark Hole"),
    SUPPLY_SQUAD("Supply Squad"),
    SPELL_ABSORPTION("Spell Absorption"),
    MESSENGER_OF_PEACE("Messenger of peace"),
    TWIN_TWISTERS("Twin Twisters"),
    MYSTICAL_SPACE_TYPHOON("Mystical space typhoon"),
    RING_OF_DEFENCE("Ring of defense"),
    YAMI("Yami"),
    FOREST("Forest"),
    CLOSED_FOREST("Closed Forest"),
    UMIIRUKA("Umiiruka"),
    SWORD_OF_DARK_DESTRUCTION("Sword of dark destruction"),
    BLACK_PENDANT("Black Pendant"),
    UNITED_WE_STAND("United We Stand"),
    MAGNUM_SHIELD("Magnum Shield"),
    ADVANCED_RITUAL_ART("Advanced Ritual Art");

    private final String cardName;

    Effect(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}