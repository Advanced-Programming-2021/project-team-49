package controller;

import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectHandler {

    protected final int speed;
    protected final Card card;
    protected final Field field;
    protected final DuelController controller;

    public EffectHandler(int speed, Card card, Field field, DuelController controller) {
        this.speed = speed;
        this.card = card;
        this.field = field;
        this.controller = controller;
    }

    public Card getCard() {
        return card;
    }

    public int getSpeed() {
        return speed;
    }

    public Card selectCardFromList(List<Card> cards) {
        DuelView.showCardListStringView(cards);
        int selected;
        do {
            selected = DuelView.selectNumber(1, cards.size());
            if (selected == 0)
                throw new GameErrorException("cancelled");
        } while (selected == -1);
        return cards.get(selected - 1);
    }

    public void moveCardToGraveyard() {
        field.getAttackerMat().removeCard(card, Location.SPELL_AND_TRAP_ZONE);
        field.getAttackerMat().addCard(card, Location.GRAVEYARD);
    }

    public List<Card> getBothMonsterZones() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(field.getAttackerMat().getCardList(Location.MONSTER_ZONE));
        cards.addAll(field.getDefenderMat().getCardList(Location.MONSTER_ZONE));
        return cards;
    }

    public boolean askForActivation() {
        int selected;
        do {
            selected = DuelView.selectAnOption(new String[]{"Activate", "Don't Activate"});
        } while (selected == -1);

        return selected == 1;
    }

    public boolean canBeActivated(Event event) {
     return true;
    }

    public abstract void activationRequirement();

    public abstract void action();

    public abstract void notifier(Event event);

    public abstract void deActivate();
}