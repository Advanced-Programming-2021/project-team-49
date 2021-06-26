package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.ArrayList;
import java.util.List;

public class DarkHole extends EffectHandler {

    private final List<Card> bothMonsterZones;

    public DarkHole(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        bothMonsterZones = getBothMonsterZones();
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (bothMonsterZones.isEmpty())
            throw new GameErrorException("There is no monster on the field");
    }

    @Override
    public void action() {
        for (Card card : new ArrayList<>(field.getAttackerMat().getCardList(Location.MONSTER_ZONE)))
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        for (Card card : new ArrayList<>(field.getDefenderMat().getCardList(Location.MONSTER_ZONE))) {
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        }

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
