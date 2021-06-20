package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

public class Raigeki extends EffectController {

    public Raigeki(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        for (Card card : field.getDefenderMat().getCardList(Location.MONSTER_ZONE))
            field.getDefenderMat().removeCard(card, Location.MONSTER_ZONE);
    }
}
