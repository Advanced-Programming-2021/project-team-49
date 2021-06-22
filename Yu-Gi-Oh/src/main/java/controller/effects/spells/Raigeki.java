package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class Raigeki extends EffectController {

    private final List<Card> enemyMonsterZone;

    public Raigeki(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        enemyMonsterZone = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (enemyMonsterZone.isEmpty())
            throw new GameErrorException("The enemy has no monster");
    }

    @Override
    public void action() {
        for (Card card : field.getDefenderMat().getCardList(Location.MONSTER_ZONE))
            field.getDefenderMat().removeCard(card, Location.MONSTER_ZONE);

        moveCardToGraveyard();
    }
}
