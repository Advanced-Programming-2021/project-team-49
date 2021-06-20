package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class ChangeOfHeart extends EffectController {

    private Card enemyCard;

    public ChangeOfHeart(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        List<Card> enemyMonsters = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);

        if (enemyMonsters.isEmpty())
            throw new GameErrorException("There is no card on enemy's monster zone");
        if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");

        enemyCard = selectCardFromList(enemyMonsters);

        field.getDefenderMat().removeCard(enemyCard, Location.MONSTER_ZONE);
        field.getAttackerMat().addCard(enemyCard, Location.MONSTER_ZONE);
    }

    public void notifier(Event event) {
        if (event != Event.END_TURN)
            return;

        field.getAttackerMat().removeCard(enemyCard, Location.MONSTER_ZONE);
        field.getDefenderMat().addCard(enemyCard, Location.MONSTER_ZONE);
    }
}
