package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class ClosedForest extends EffectHandler {

    private int monstersInGraveyard = 0;
    private final List<Card> bothMonsterZones;

    public ClosedForest(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        EffectHandler effect = field.getAttackerMat().getFieldZoneEffect();
        if (effect != null)
            effect.deActivate();
        field.getAttackerMat().setFieldZoneEffect(this);

        bothMonsterZones = getBothMonsterZones();
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                monstersInGraveyard++;
        }

        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.BEAST)
                monster.increaseAttack(monstersInGraveyard * 100);
        }

        field.getDefenderMat().addLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATED);
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {
        for (Card card : bothMonsterZones) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.BEAST)
                monster.decreaseAttack(monstersInGraveyard * 100);
        }

        field.getDefenderMat().removeLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATED);
    }
}
