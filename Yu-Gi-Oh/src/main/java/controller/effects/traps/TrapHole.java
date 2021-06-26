package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class TrapHole extends EffectHandler {
    public TrapHole(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        field.getAttackerMat().moveCard(Location.MONSTER_ZONE, controller.getSelectedCard(), Location.GRAVEYARD);
        controller.getField().getDefenderMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.NORMAL_SUMMON && event != Event.FLIP_SUMMON)
            return;

        if (askForActivation())
            action();
    }

    @Override
    public void deActivate() {

    }

    @Override
    public boolean canBeActivated(Event event) {
        return event == Event.NORMAL_SUMMON || event == Event.FLIP_SUMMON;
    }
}
