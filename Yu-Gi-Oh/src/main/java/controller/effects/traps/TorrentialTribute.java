package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.ArrayList;

public class TorrentialTribute extends EffectHandler {
    public TorrentialTribute(int speed, Card card, Field field,
            DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : new ArrayList<>(field.getAttackerMat().getCardList(Location.MONSTER_ZONE)))
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        for (Card card : new ArrayList<>(field.getDefenderMat().getCardList(Location.MONSTER_ZONE)))
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);

        controller.getField().getDefenderMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.SUMMON)
            return;

        if (askForActivation())
            action();
    }

    @Override
    public void deActivate() {

    }

    @Override
    public boolean canBeActivated(Event event) {
        return event == Event.SUMMON;
    }
}
