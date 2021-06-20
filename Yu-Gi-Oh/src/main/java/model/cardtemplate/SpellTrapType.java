package model.cardtemplate;

public enum SpellTrapType {
    SPELL("Spell"),
    TRAP("Trap");

    private final String type;

    SpellTrapType(String type) {
         this.type = type;
    }

    public String getType() {
        return type;
    }
}
