package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class Umiiruka extends EffectController {

    private final List<Card> bothMonsterZones;

    public Umiiruka(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        EffectController effect = field.getAttackerMat().getFieldZoneEffect();
        if (effect != null)
            effect.deActive();
        field.getAttackerMat().setFieldZoneEffect(this);

        bothMonsterZones = getBothMonsterZones();
    }

    @Override
    public void action() {
        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.AQUA) {
                monster.increaseAttack(500);
                monster.decreaseDefense(400);
            }
        }
    }

    @Override
    public void deActive() {
        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.AQUA) {
                monster.decreaseAttack(500);
                monster.increaseDefense(400);
            }
        }
    }
}
