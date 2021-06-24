package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class ExploderDragon extends EffectHandler {

    private Card enemyToBeDestroyed;

    public ExploderDragon(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        //TODO diagnose if the card is attacker or defender.
        //TODO destroy the enemy's card.
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public void getRequirements(Card enemy) {
        enemyToBeDestroyed = enemy;
    }
}
