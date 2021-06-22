package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import model.cardtemplate.Attribute;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class Umiiruka extends EffectController {

    public Umiiruka(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        EffectController effect = field.getAttackerMat().getFieldZoneEffect();
        if (effect != null)
            effect.deActive();
        field.getAttackerMat().setFieldZoneEffect(this);

        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getAttribute() == Attribute.WATER) {
                monster.increaseAttack(500);
                monster.decreaseDefense(400);
            }
        }
    }

    @Override
    public void deActive() {
        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getAttribute() == Attribute.WATER) {
                monster.increaseAttack(500);
                monster.increaseDefense(400);
            }
        }
    }
}
