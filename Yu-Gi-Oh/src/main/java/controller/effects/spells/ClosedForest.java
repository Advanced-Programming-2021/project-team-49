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

public class ClosedForest extends EffectHandler {

    public ClosedForest(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().setFieldZoneEffect(this);
        field.getAttackerMat().moveCard(Location.HAND, card, Location.FIELD_ZONE);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        for (Card card : getBothMonsterZones())
            modify((Monster) card);

        field.getDefenderMat().addLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATED);
    }

    @Override
    public void notifier(Event event) {
        for (Card card : getBothMonsterZones()) {
            Monster monster = (Monster) card;
            monster.setAttackModifier(0);
            modify(monster);
        }
    }

    @Override
    public void deActivate() {
        for (Card card : getBothMonsterZones()) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.BEAST)
                monster.decreaseAttack(getMonstersInGraveyardCount() * 100);
        }

        field.getDefenderMat().removeLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATED);
    }

    private void modify(Monster monster) {
        if (monster.getMonsterType() == MonsterType.BEAST)
            monster.increaseAttack(getMonstersInGraveyardCount() * 100);
    }

    public int getMonstersInGraveyardCount() {
        int monstersInGraveyard = 0;
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                monstersInGraveyard++;
        }
        return monstersInGraveyard;
    }
}
