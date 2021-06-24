package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class ManEaterBug extends EffectHandler {

    private final List<Card> enemyMonsterZone;

    public ManEaterBug(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        enemyMonsterZone = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (!card.isFaceUp())
            throw new GameErrorException();
        if (enemyMonsterZone.isEmpty())
            throw new GameErrorException();
    }

    @Override
    public void action() {
        Card card = selectCardFromList(enemyMonsterZone);

        field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
