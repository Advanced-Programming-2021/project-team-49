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
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
            if (((Monster) card).isAttacker())
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        moveCardToGraveyard();
        throw new StopAttackException("Mirror Force destroyed all attack position cards and the attack is stopped");
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.DECLARED_ATTACK)
            if (askForActivation())
                action();
    }

    @Override
    public void deActivate() {

    }
}
