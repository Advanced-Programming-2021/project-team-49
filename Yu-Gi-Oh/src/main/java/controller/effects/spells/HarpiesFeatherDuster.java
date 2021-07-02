package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.ArrayList;
import java.util.List;

public class HarpiesFeatherDuster extends EffectHandler {

    private final List<Card> enemySpellZone;

    public HarpiesFeatherDuster(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        enemySpellZone = field.getDefenderMat().getCardList(Location.SPELL_AND_TRAP_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (enemySpellZone.isEmpty())
            throw new GameErrorException("The enemy has no spell and trap");
    }

    @Override
    public void action() {
        for (Card card : new ArrayList<>(enemySpellZone))
            field.getDefenderMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
