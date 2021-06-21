package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class Yami extends EffectController {

    public Yami(Card card, Field field, DuelController controller) {
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
            if (monster.getMonsterType() == MonsterType.SPELLCASTER
                    || monster.getMonsterType() == MonsterType.FIEND) {
                monster.setAttack(200);
                monster.setDefense(200);
            } else if (monster.getMonsterType() == MonsterType.FAIRY) {
                monster.setAttack(-200);
                monster.setDefense(-200);
            }
        }
    }

    @Override
    public void deActive() {
        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.SPELLCASTER
                    || monster.getMonsterType() == MonsterType.FIEND) {
                monster.setAttack(0);
                monster.setDefense(0);
            } else if (monster.getMonsterType() == MonsterType.FAIRY) {
                monster.setAttack(0);
                monster.setDefense(0);
            }
        }
    }
}
