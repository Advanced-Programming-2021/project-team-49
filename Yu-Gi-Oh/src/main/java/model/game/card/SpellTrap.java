package model.game.card;

import model.cardtemplate.SpellTrapCard;

public class SpellTrap extends SpellTrapCard implements Castable {

    private boolean faceUp;

    public SpellTrap(SpellTrapCard card) {
        super(card.getName(), card.getDescription(), card.getEffect(), card.getEffectType(), card.getStatus(), card.getType());
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
}
