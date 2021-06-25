package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.StopAttackException;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

public class MagicCylinder extends EffectHandler {

    Monster attacker;

    public MagicCylinder(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        attacker = (Monster) controller.getSelectedCard();

        field.getAttackerMat().getPlayer().removeLifePoints(attacker.getTotalAttack());
        controller.checkEndOfRoundWithLifePoints();
        moveCardToGraveyard();
        throw new StopAttackException("Magic Cylinder stopped the attack");
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.DECLARED_ATTACK)
            return;

        if (askForActivation())
            action();
    }

    @Override
    public void deActivate() {

    }

    @Override
    public boolean canBeActivated(Event event) {
        return event == Event.DECLARED_ATTACK;
    }
}
