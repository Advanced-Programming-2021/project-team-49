package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.EndPhaseException;
import model.game.Field;
import model.game.card.Card;

public class TimeSeal extends EffectHandler {
    public TimeSeal(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        throw new EndPhaseException("Time Seal stopped the draw phase");
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.DRAW_PHASE)
            return;

        action();
    }

    @Override
    public void deActivate() {

    }

    @Override
    public boolean canBeActivated(Event event) {
        return event == Event.DRAW_PHASE;
    }
}
