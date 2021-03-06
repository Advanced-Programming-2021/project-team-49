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

public class Raigeki extends EffectHandler {

    private final List<Card> enemyMonsterZone;

    public Raigeki(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        enemyMonsterZone = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (enemyMonsterZone.isEmpty())
            throw new GameErrorException("The enemy has no monster");
    }

    @Override
    public void action() {
        for (Card card : new ArrayList<>(field.getDefenderMat().getCardList(Location.MONSTER_ZONE)))
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
