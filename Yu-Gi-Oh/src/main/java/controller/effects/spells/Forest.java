package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class Forest extends EffectController {

    private final List<Card> bothMonsterZones;

    public Forest(Card card, Field field, DuelController controller) {
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
            if (monster.getMonsterType() == MonsterType.INSECT
                    || monster.getMonsterType() == MonsterType.BEAST
                    || monster.getMonsterType() == MonsterType.BEAST_WARRIOR) {
                monster.increaseAttack(200);
                monster.increaseDefense(200);
            }
        }
    }

    @Override
    public void deActive() {
        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.INSECT
                    || monster.getMonsterType() == MonsterType.BEAST
                    || monster.getMonsterType() == MonsterType.BEAST_WARRIOR) {
                monster.decreaseAttack(200);
                monster.decreaseDefense(200);
            }
        }
    }
}
