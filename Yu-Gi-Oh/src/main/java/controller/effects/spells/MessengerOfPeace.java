package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.Field;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class MessengerOfPeace extends EffectHandler {

    public MessengerOfPeace(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        field.getAttackerMat().addActivatedEffect(this);
        field.getDefenderMat().addLimit(Limit.MONSTERS_WITH_1500_ATK_OR_MORE_CANT_ATTACK);
    }

    @Override
    public void notifier(Event event) {
        if (event != Event.STANDBY_PHASE)
            return;


        final String[] selected = new String[1];
        List<String> options = new ArrayList<>();
        options.add("Pay 100 LP to keep the card");
        options.add("Destroy the card");
        field.getAttackerMat().getDuelView().selectAnOption(
                "Select:", options, selectedOption -> {
                    for (String option : options) {
                        if (selectedOption.equals(option))
                            selected[0] = option;
                    }
                });

        if (selected[0].startsWith("Pay"))
            field.getAttackerMat().getPlayer().removeLifePoints(100);
        else {
            field.getDefenderMat().removeLimit(Limit.MONSTERS_WITH_1500_ATK_OR_MORE_CANT_ATTACK);
            moveCardToGraveyard();
        }
    }

    @Override
    public void deActivate() {

    }
}
