package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class SwordsOfRevealingLight extends EffectController {

    private int turnCounter = 0;

    public SwordsOfRevealingLight(Card card,Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        for (Card card : field.getDefenderMat().getCardList(Location.MONSTER_ZONE)) {
            if (!card.isFaceUp())
                card.setFaceUp(true);
        }
        field.getDefenderMat().addLimit(Limit.ALL_MONSTERS_CANT_ATTACK);
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.END_TURN)
            turnCounter++;

        if (turnCounter == 6) {
            field.getDefenderMat().removeLimit(Limit.ALL_MONSTERS_CANT_ATTACK);
            moveCardToGraveyard();
        }
    }
}
