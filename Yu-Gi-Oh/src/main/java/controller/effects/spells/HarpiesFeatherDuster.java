package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class HarpiesFeatherDuster extends EffectController {

    public HarpiesFeatherDuster(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        List<Card> spellsAndTraps = field.getDefenderMat().getCardList(Location.SPELL_AND_TRAP_ZONE);
        if (spellsAndTraps.isEmpty())
            throw new GameErrorException("The enemy has no spells");

        for (Card card : spellsAndTraps)
            field.getDefenderMat().removeCard(card, Location.SPELL_AND_TRAP_ZONE);

        moveCardToGraveyard();
    }
}
