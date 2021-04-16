package model.card;

public abstract class CardTemplate {

    protected final String name;
    protected final String number;
    protected final Effect effect;
    protected final String description;

    public CardTemplate(String name, String number, String description, Effect effect) {
        this.name = name;
        this.number = number;
        this.description = description;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}