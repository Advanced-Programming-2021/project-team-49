package model.game.card;

import model.cardtemplate.CardTemplate;
import model.cardtemplate.Effect;

public abstract class Card {

    protected boolean faceUp;
    protected final CardTemplate cardTemplate;

    public Card(CardTemplate cardTemplate) {
        this.cardTemplate = cardTemplate;
        faceUp = false;
    }

    public String getName() {
        return cardTemplate.getName();
    }

    public String getDescription() {
        return cardTemplate.getDescription();
    }

    public Effect getEffect() {
        return cardTemplate.getEffect();
    }

    public String getCardPicPath() {
        return cardTemplate.getCardPicPath();
    }

    public int getPrice() {
        return cardTemplate.getPrice();
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp() {
        faceUp = true;
    }
}
