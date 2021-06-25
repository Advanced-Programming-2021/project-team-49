package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;

public class BeastKingBarbaros extends EffectHandler {

    public BeastKingBarbaros(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {

    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
