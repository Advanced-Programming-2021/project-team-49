package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.ArrayList;
import java.util.List;

public class SwordOfDarkDestruction extends EffectHandler {

    private final List<Card> requiredMonsters;

    public SwordOfDarkDestruction(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        requiredMonsters = getRequiredMonsters();
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (requiredMonsters.isEmpty())
            throw new GameErrorException("you don't have any \"Fiend\" or \"Spellcaster\" monster");
    }

    @Override
    public void action() {
        Card card = selectCardFromList(requiredMonsters);

        ((Monster) card).increaseAttack(400);
        ((Monster) card).decreaseDefense(200);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    private List<Card> getRequiredMonsters() {
        List<Card> requiredMonsters = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE)) {
            Monster monster = (Monster) card;
            if (monster.getMonsterType() == MonsterType.SPELLCASTER
                    || monster.getMonsterType() == MonsterType.FIEND)
                requiredMonsters.add(card);
        }
        return requiredMonsters;
    }
}
