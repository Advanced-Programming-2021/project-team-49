package model.game.card;

import model.cardtemplate.*;

public class Monster extends MonsterCard implements Castable {

    private boolean faceUp;
    private boolean attacker;
    private boolean effect;
    private boolean PositionChanged;

    public Monster(MonsterCard card) {
        super(card.getName(), card.getDescription(), card.getEffect(), card.getLevel(), card.getAttribute(),
                card.getCardType(), card.getMonsterType(), card.getBaseAttack(), card.getBaseDefence());
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void setAttacker(boolean attacker) {
        this.attacker = attacker;
    }

    public boolean hasEffect() {
        return effect;
    }

    public void setEffect(boolean effect) {
        this.effect = effect;
    }

    public boolean isPositionChanged() {
        return PositionChanged;
    }

    public void setPositionChanged(boolean positionChanged) {
        PositionChanged = positionChanged;
    }
}
