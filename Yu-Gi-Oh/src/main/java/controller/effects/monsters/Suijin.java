package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

public class Suijin extends EffectHandler {

    private Monster target;
    private int decreasedAttack;
    private boolean activated = false;

    public Suijin(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        target = (Monster) controller.getSelectedCard();
        decreasedAttack = target.getTotalAttack();
        target.decreaseAttack(decreasedAttack);
        activated = true;
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.DECLARED_ATTACK) {
            if (!activated && target == null)
                if (askForActivation())
                    action();
        } else if (event == Event.END_TURN && activated)
            deActivate();
    }

    @Override
    public void deActivate() {
        target.increaseAttack(decreasedAttack);
        activated = false;
    }
}
