package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class DarkHole extends EffectController {

    public DarkHole(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        List<Card> monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        List<Card> enemyMonsterZone = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);

        if (monsterZone.isEmpty() && enemyMonsterZone.isEmpty())
            throw new GameErrorException("There is no monster on the field");

        for (Card card : monsterZone)
            field.getAttackerMat().removeCard(card, Location.MONSTER_ZONE);
        for (Card card : enemyMonsterZone) {
            field.getDefenderMat().removeCard(card, Location.MONSTER_ZONE);
        }
    }
}
