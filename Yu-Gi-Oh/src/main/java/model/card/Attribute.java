package model.card;

public enum Attribute {
    DARK("DARK"),
    EARTH("EARTH"),
    FIRE("FIRE"),
    LIGHT("LIGHT"),
    WATER("WATER"),
    WIND("WIND");

    private final String attribute;

    Attribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
