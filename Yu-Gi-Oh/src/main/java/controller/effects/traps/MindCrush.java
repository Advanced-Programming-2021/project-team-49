package controller.effects.traps;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MindCrush extends EffectHandler {

    public MindCrush(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        field.getAttackerMat().addActivatedEffect(this);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        List<CardTemplate> cards = controller.getMasterController().getDatabase().getCards();
        DuelView.showCardNameListStringView(cards);
        int selected;
        do {
            selected = DuelView.selectNumber(1, cards.size());
            if (selected == 0)
                throw new GameErrorException("cancelled");
        } while (selected == -1);
        String cardName = cards.get(selected - 1).getName();

        boolean isAnyCardRemoved = false;
        // TODO check if the trap is activated in own turn or enemy's turn. here is for own turn (use field.switchMat())

        for (Card card : new ArrayList<>(field.getDefenderMat().getCardList(Location.HAND))) {
            if (card.getName().equals(cardName)) {
                field.getDefenderMat().moveCard(Location.HAND, card, Location.GRAVEYARD);
                isAnyCardRemoved = true;
            }
        }
        if (!isAnyCardRemoved) {
            int random = new Random().nextInt(field.getAttackerMat().getCardCount(Location.HAND) - 1);
            field.getAttackerMat().moveCard(Location.HAND, random, Location.GRAVEYARD);
        }
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    @Override
    public boolean canBeActivated(Event event) {
        return true;
    }
}
