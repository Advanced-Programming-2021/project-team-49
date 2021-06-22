package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class BlackPendant extends EffectController {

    private final List<Card> monsterZone;

    public BlackPendant(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.isEmpty())
            throw new GameErrorException("monster zone is empty");
    }

    @Override
    public void action() {
        Card card = selectCardFromList(monsterZone);

        ((Monster) card).increaseAttack(500);

        moveCardToGraveyard();
    }
}
