package controller;

import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class EffectController {

    protected final Card card;
    protected final Field field;
    protected final DuelController controller;

    public EffectController(Card card, Field field, DuelController controller) {
        this.card = card;
        this.field = field;
        this.controller = controller;
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
    
    public void notifyEffects(Event event) {
        for (EffectController effect : field.getAttackerMat().getActivatedEffects())
            effect.notifier(event);
        for (EffectController effect : field.getDefenderMat().getActivatedEffects())
            effect.notifier(event);
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

    public void activationRequirement() {}

    public void action() {}

    public void notifier(Event event) {}

    public void deActive() {}
}