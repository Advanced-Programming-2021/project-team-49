package controller.effects;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;

public class PotOfGreed extends EffectController {

    public PotOfGreed(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        if (field.getAttackerMat().getCardCount(Location.DECK) < 2)
            throw new GameErrorException("Not enough cards in deck");

        controller.drawCard();
        controller.drawCard();
    }
}
