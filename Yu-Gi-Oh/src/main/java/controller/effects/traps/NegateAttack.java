package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.EndPhaseException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class NegateAttack extends EffectHandler {

    public NegateAttack(int speed, Card card, Field field,
            DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        controller.getField().getDefenderMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);
        throw new EndPhaseException("Negate Attack ended the battle phase");
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
