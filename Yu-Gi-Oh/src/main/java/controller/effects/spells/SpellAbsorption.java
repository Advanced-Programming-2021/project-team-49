package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class SpellAbsorption extends EffectController {

    public SpellAbsorption(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.A_SPELL_ACTIVATED)
            return;

        field.getAttackerMat().getPlayer().addLifePoints(500);
    }
}
