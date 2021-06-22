package controller.effects.spells;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import controller.effects.Limit;
import model.game.Field;
import model.game.card.Card;
import view.DuelView;

public class MessengerOfPeace extends EffectHandler {

    public MessengerOfPeace(Card card, Field field, DuelController controller) {
        super(card, field, controller);
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

        int selected;
        do {
            selected = DuelView.selectAnOption(new String[]
                    {"Pay 100 LP to keep the card",
                    "Destroy the card"});

        } while (selected == 0 || selected == -1);

        if (selected == 1)
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
