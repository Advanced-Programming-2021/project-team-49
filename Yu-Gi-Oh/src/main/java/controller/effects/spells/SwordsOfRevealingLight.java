package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class SwordsOfRevealingLight extends EffectHandler {

    private int turnCounter = 0;

    public SwordsOfRevealingLight(int speed, Card card,Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : field.getDefenderMat().getCardList(Location.MONSTER_ZONE)) {
            if (!card.isFaceUp())
                card.setFaceUp();
        }

        field.getAttackerMat().addActivatedEffect(this);
        field.getDefenderMat().addLimit(Limit.ALL_MONSTERS_CANT_ATTACK);
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.END_TURN)
            turnCounter++;

        if (turnCounter == 3) {
            field.getDefenderMat().removeLimit(Limit.ALL_MONSTERS_CANT_ATTACK);
            moveCardToGraveyard();
        }
    }

    @Override
    public void deActivate() {

    }
}
