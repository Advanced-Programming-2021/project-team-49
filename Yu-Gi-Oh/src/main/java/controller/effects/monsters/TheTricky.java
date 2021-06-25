package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class TheTricky extends EffectHandler {

    private final List<Card> hand;

    public TheTricky(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        hand = field.getAttackerMat().getCardList(Location.HAND);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (hand.isEmpty())
            throw new GameErrorException();
    }

    @Override
    public void action() {
        Card selectCard = selectCardFromList(hand);

        field.getAttackerMat().moveCard(Location.HAND, selectCard, Location.GRAVEYARD);

        field.getAttackerMat().moveCard(Location.HAND, card, Location.MONSTER_ZONE);

    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
