package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class SupplySquad extends EffectHandler {

    public SupplySquad(int speed, Card card, Field field, DuelController controller) {
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
        if (event != Event.A_MONSTER_DESTROYED)
            return;

        controller.drawCard();
    }

    @Override
    public void deActivate() {

    }
}
