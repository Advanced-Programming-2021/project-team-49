package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

public class Suijin extends EffectHandler {

    private Monster attacker;
    private int decreasedAttack;
    private boolean activated = false;
    private boolean activatedInThisTurn = false;

    public Suijin(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    public void setAttacker(Monster attacker) {
        this.attacker = attacker;
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        if (activated)
            return;
        if (!askForActivation())
            return;

        attacker = (Monster) controller.getSelectedCard();
        decreasedAttack = attacker.getTotalAttack();
        attacker.decreaseAttack(decreasedAttack);
        activated = true;
        activatedInThisTurn = true;
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.END_TURN && activatedInThisTurn)
            deActivate();
    }

    @Override
    public void deActivate() {
        attacker.increaseAttack(decreasedAttack);
        activatedInThisTurn = false;
    }
}
