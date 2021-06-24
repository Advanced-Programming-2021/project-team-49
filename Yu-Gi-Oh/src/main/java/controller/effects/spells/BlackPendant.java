package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class BlackPendant extends EffectHandler {

    private final List<Card> monsterZone;

    public BlackPendant(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.isEmpty())
            throw new GameErrorException("monster zone is empty");
    }

    @Override
    public void action() {
        Card card = selectCardFromList(monsterZone);

        ((Monster) card).increaseAttack(500);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
