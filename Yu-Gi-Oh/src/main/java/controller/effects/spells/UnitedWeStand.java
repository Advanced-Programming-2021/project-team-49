package controller.effects.spells;

import controller.DuelController;
import controller.EffectController;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.List;

public class UnitedWeStand extends EffectController {

    private final List<Card> monsterZone;

    public UnitedWeStand(Card card, Field field, DuelController controller) {
        super(card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.isEmpty())
            throw new GameErrorException("monster zone is empty");
        // TODO check if the selected card must be face up and it counts or not
    }

    @Override
    public void action() {
        Card card = selectCardFromList(monsterZone);

        ((Monster) card).increaseAttack(getFaceUpMonstersCount() * 800);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {
        super.notifier(event);
        // TODO check if face up monsters count changes and do the changes
    }

    private int getFaceUpMonstersCount() {
        int faceUpMonstersCount = 0;

        for (Card card : monsterZone) {
            if (card.isFaceUp())
                faceUpMonstersCount++;
        }
        return faceUpMonstersCount;
    }
}
