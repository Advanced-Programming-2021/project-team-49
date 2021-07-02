package controller.effects.monsters;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class GateGuardian extends EffectHandler {

    private final List<Card> monsterZone;

    public GateGuardian(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.size() < 3)
            throw new GameErrorException();
    }

    @Override
    public void action() {
        List<Card> selectedCards = select3CardsFromList(monsterZone);

        for (Card selectedCard : selectedCards)
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selectedCard, Location.GRAVEYARD);

        field.getAttackerMat().moveCard(Location.HAND, card, Location.MONSTER_ZONE);
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
