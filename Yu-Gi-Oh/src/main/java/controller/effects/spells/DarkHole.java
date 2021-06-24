package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

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
        List<Card> monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        List<Card> enemyMonsterZone = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);

        for (Card card : monsterZone)
            field.getAttackerMat().removeCard(card, Location.MONSTER_ZONE);
        for (Card card : enemyMonsterZone) {
            field.getDefenderMat().removeCard(card, Location.MONSTER_ZONE);
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
