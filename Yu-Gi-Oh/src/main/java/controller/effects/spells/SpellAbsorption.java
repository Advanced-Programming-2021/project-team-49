package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class SpellAbsorption extends EffectHandler {

    public SpellAbsorption(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

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

    @Override
    public void deActivate() {

    }
}
