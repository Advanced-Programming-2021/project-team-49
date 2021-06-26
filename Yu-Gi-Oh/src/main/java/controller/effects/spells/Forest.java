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

public class Forest extends EffectHandler {

    private final List<Card> bothMonsterZones;

    public Forest(int speed, Card card, Field field, DuelController controller) {
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
        for (Card card : bothMonsterZones)
            modify((Monster) card);
    }

    @Override
    public void notifier(Event event) {
        modify((Monster) field.getAttackerMat().getCard(Location.MONSTER_ZONE));
    }

    @Override
    public void deActivate() {
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

    private void modify(Monster monster) {
        if (monster.getMonsterType() == MonsterType.INSECT
                || monster.getMonsterType() == MonsterType.BEAST
                || monster.getMonsterType() == MonsterType.BEAST_WARRIOR) {
            monster.increaseAttack(200);
            monster.increaseDefense(200);
        }
    }
}
