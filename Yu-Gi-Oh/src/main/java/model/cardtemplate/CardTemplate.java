package model.cardtemplate;

public abstract class CardTemplate {

    protected final String name;
    protected final Effect effect;
    protected final String description;
    protected final String cardPicPath;
    protected final int price;

    public CardTemplate(String name, String description, Effect effect, String cardPicPath, int price) {
        this.name = name;
        this.description = description;
        this.effect = effect;
        this.cardPicPath = cardPicPath;
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