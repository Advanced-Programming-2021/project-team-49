package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

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
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        for (Card card : field.getDefenderMat().getCardList(Location.MONSTER_ZONE))
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.SUMMON)
            action();
    }

    @Override
    public void deActivate() {

    }
}
