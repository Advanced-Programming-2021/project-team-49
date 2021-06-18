package model.cardtemplate;

import java.util.ArrayList;

public abstract class Card {

    protected final String name;
    protected final Effect effect;
    protected final String description;
    protected static final ArrayList<Card> allCards;

    static {
        allCards = new ArrayList<>();
    }

    public Card(String name, String description, Effect effect) {
        this.name = name;
        this.description = description;
        this.effect = effect;
        allCards.add(this);
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