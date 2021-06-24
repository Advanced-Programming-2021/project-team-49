package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.card.Card;
import model.game.card.Monster;
import view.DuelView;

public class MagicCylinder extends EffectHandler {

    Monster attacker;

    public MagicCylinder(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        attacker = (Monster) controller.getSelectedCard();

        field.getAttackerMat().getPlayer().removeLifePoints(attacker.getTotalAttack());

    }

    @Override
    public void notifier(Event event) {
        if (event != Event.DECLARED_ATTACK)
            return;

        int selected;
        do {
            selected = DuelView.selectAnOption(new String[]{"Activate", "Don't Activate"});
        } while (selected == 0 || selected == -1);

        if (selected == 1)
            action();
    }

    @Override
    public void deActivate() {

    }
}
