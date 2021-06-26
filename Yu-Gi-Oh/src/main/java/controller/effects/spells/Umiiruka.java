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

public class Umiiruka extends EffectHandler {

    private final List<Card> bothMonsterZones;

    public Umiiruka(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().setFieldZoneEffect(this);
        field.getAttackerMat().moveCard(Location.HAND, card, Location.FIELD_ZONE);

        bothMonsterZones = getBothMonsterZones();
    }

    @Override
    public void activationRequirement() {

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
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {
        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.AQUA) {
                monster.decreaseAttack(500);
                monster.increaseDefense(400);
            }
        }
    }
}
