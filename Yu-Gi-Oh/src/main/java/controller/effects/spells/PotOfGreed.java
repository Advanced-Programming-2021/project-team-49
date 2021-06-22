package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class PotOfGreed extends EffectHandler {

    public PotOfGreed(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (field.getAttackerMat().getCardCount(Location.DECK) < 2)
            throw new GameErrorException("Not enough cards in deck");
    }

    @Override
    public void action() {
        controller.drawCard();
        controller.drawCard();

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
