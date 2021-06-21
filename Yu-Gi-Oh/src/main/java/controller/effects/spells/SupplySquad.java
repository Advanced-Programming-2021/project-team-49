package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class SupplySquad extends EffectController {

    public SupplySquad(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.A_MONSTER_DESTROYED)
            return;

        controller.drawCard();
    }
}