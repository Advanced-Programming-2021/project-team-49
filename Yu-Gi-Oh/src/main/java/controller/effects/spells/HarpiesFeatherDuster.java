package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class HarpiesFeatherDuster extends EffectController {

    private final List<Card> enemySpellZone;

    public HarpiesFeatherDuster(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        enemySpellZone = field.getDefenderMat().getCardList(Location.SPELL_AND_TRAP_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (enemySpellZone.isEmpty())
            throw new GameErrorException("The enemy has no spell and trap");
    }

    @Override
    public void action() {
        for (Card card : enemySpellZone)
            field.getDefenderMat().removeCard(card, Location.SPELL_AND_TRAP_ZONE);

        moveCardToGraveyard();
    }
}
