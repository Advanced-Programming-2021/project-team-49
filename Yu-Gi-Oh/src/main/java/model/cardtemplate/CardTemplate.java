package model.cardtemplate;

public abstract class CardTemplate {

    protected final String name;
    protected final Effect effect;
    protected final String description;
    protected final int price;

    public CardTemplate(String name, String description, Effect effect, int price) {
        this.name = name;
        this.description = description;
        this.effect = effect;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getPrice() {
        return price;
    }
}