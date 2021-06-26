package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.StopAttackException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

public class MirrorForce extends EffectHandler {

    public MirrorForce(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
            if (((Monster) card).isAttacker())
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        controller.getField().getDefenderMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);
        throw new StopAttackException("Mirror Force destroyed all attack position cards and the attack is stopped");
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
