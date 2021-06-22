package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Limit;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class ClosedForest extends EffectController {

    public ClosedForest(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    private int monstersInGraveyard = 0;

    @Override
    public void action() {
        EffectController effect = field.getAttackerMat().getFieldZoneEffect();
        if (effect != null)
            effect.deActive();
        field.getAttackerMat().setFieldZoneEffect(this);

        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                monstersInGraveyard++;
        }

        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.BEAST)
                monster.increaseAttack(monstersInGraveyard * 100);
        }

        field.getDefenderMat().addLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATE);
    }

    @Override
    public void deActive() {
        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.BEAST)
                monster.decreaseAttack(monstersInGraveyard * 100);
        }

        field.getDefenderMat().removeLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATE);
    }
}
