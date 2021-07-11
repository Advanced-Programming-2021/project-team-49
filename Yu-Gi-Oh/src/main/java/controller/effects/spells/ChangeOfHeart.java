package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;

import java.util.List;

public class ChangeOfHeart extends EffectHandler {

    private Card enemyCard;
    private final List<Card> enemyMonsters;

    public ChangeOfHeart(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        enemyMonsters = field.getDefenderMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (enemyMonsters.isEmpty())
            throw new GameErrorException("There is no card on enemy's monster zone");

        if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
    }

    @Override
    public void action() {
        enemyCard = selectCardFromList("Select a monster to be yours for 1 turn:",
                enemyMonsters, 1).get(0);

        field.getDefenderMat().removeCard(enemyCard, Location.MONSTER_ZONE);
        field.getAttackerMat().addCard(enemyCard, Location.MONSTER_ZONE);

        field.getAttackerMat().addActivatedEffect(this);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.END_TURN)
            return;

        field.getAttackerMat().removeCard(enemyCard, Location.MONSTER_ZONE);
        field.getDefenderMat().addCard(enemyCard, Location.MONSTER_ZONE);
    }

    @Override
    public void deActivate() {

    }
}
