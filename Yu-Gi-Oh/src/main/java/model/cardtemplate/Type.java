package model.cardtemplate;

public enum Type {
    SPELL("Spell"),
    TRAP("Trap");

    private final String type;

    Type(String type) {
         this.type = type;
    }

    public String getType() {
        return type;
    }
}
