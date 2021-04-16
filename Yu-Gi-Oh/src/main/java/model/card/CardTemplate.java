package model.card;

public abstract class CardTemplate {
    protected final String name;
    protected final String number;
    protected final String description;

    public CardTemplate(String name, String number, String description) {
        this.name = name;
        this.number = number;
        this.description = description;
    }
}