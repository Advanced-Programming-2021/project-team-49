package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import exception.GameErrorException;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.ArrayList;
import java.util.List;

public class MagnumShield extends EffectController {

    private final List<Card> requiredMonsters;

    public MagnumShield(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        requiredMonsters = getRequiredMonsters();
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (requiredMonsters.isEmpty())
            throw new GameErrorException("you have no \"Warrior\" monster");
    }

    @Override
    public void action() {
        Monster monster = (Monster) selectCardFromList(requiredMonsters);

        if (monster.isAttacker())
            monster.increaseAttack(monster.getBaseDefense());
        else
            monster.decreaseDefense(monster.getBaseAttack());

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {
        super.notifier(event);
        // TODO check if face of the equipped card changed, it changes or not
    }

    private List<Card> getRequiredMonsters() {
        List<Card> requiredMonsters = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE)) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.WARRIOR)
                requiredMonsters.add(card);
        }
        return requiredMonsters;
    }
}
