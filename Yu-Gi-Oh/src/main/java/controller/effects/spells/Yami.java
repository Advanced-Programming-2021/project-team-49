package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class Yami extends EffectHandler {

    public Yami(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().setFieldZoneEffect(this);
        field.getAttackerMat().moveCard(Location.HAND, Location.FIELD_ZONE);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.SPELLCASTER
                    || monster.getMonsterType() == MonsterType.FIEND) {
                monster.increaseAttack(200);
                monster.increaseDefense(200);
            } else if (monster.getMonsterType() == MonsterType.FAIRY) {
                monster.decreaseAttack(200);
                monster.decreaseDefense(200);
            }
        }
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {
        List<Card> cards = getBothMonsterZones();

        for (Card card : cards) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.SPELLCASTER
                    || monster.getMonsterType() == MonsterType.FIEND) {
                monster.decreaseAttack(200);
                monster.decreaseDefense(200);
            } else if (monster.getMonsterType() == MonsterType.FAIRY) {
                monster.increaseAttack(200);
                monster.increaseDefense(200);
            }
        }
    }
}
