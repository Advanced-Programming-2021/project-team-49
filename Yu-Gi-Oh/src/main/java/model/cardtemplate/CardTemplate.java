package model.cardtemplate;

import java.util.Objects;

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

    public String getCardPicPath() {
        return getClass().getResource(cardPicPath).toExternalForm();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardTemplate)) return false;
        CardTemplate that = (CardTemplate) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}