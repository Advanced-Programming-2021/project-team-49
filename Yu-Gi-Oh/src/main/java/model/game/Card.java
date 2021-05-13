package model.game;

import model.card.CardTemplate;
import model.card.Monster;

public class Card {
    private final CardTemplate cardTemplate;
    private boolean faceUp;
    private boolean attacker;
    private boolean attackPossibility;
    private boolean effect;

    public Card(CardTemplate cardTemplate) {
        this.cardTemplate = cardTemplate;
    }

    public String getName() {
        return cardTemplate.getName();
    }

    public String getDescription() {
        return cardTemplate.getDescription();
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setAttacker(boolean attacker) {
        this.attacker = attacker;
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void setAttackPossibility(boolean attackPossibility) {
        this.attackPossibility = attackPossibility;
    }

    public boolean isAttackPossible() {
        return attackPossibility;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public boolean hasEffect() {
        return effect;
    }
}