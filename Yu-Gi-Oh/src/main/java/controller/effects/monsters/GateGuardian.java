package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import view.DuelView;

import java.util.List;

public class GateGuardian extends EffectHandler {

    private final List<Card> monsterZone;

    public GateGuardian(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.size() < 3)
            throw new GameErrorException();
    }

    @Override
    public void action() {
        int selected1 = DuelView.selectNumber(1, monsterZone.size());
        int selected2 = DuelView.selectNumber(1, monsterZone.size());
        int selected3 = DuelView.selectNumber(1, monsterZone.size());

        if (selected1 == -1 || selected2 == -1 || selected3 == -1
                || selected1 == selected2 || selected1 == selected3)
            throw new GameErrorException("there is no monster on one of these addresses");

        field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected1, Location.GRAVEYARD);
        field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected2, Location.GRAVEYARD);
        field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected3, Location.GRAVEYARD);

        field.getAttackerMat().moveCard(Location.HAND, card, Location.MONSTER_ZONE);
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }
}
