package model.cardtemplate;

public abstract class CardTemplate {

    protected final String name;
    protected final Effect effect;
    protected final String description;

    public CardTemplate(String name, String description, Effect effect) {
        this.name = name;
        this.description = description;
        this.effect = effect;
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
}