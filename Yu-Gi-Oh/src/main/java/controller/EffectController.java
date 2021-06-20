package controller;

import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class EffectController {

    protected final Field field;
    protected final DuelController controller;
    private List<EffectController> activatedEffects = new ArrayList<>();
    
    public EffectController(Field field, DuelController controller) {
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
        for (EffectController effect : activatedEffects)
            effect.notifier(event);
    }

    protected void action() {}

    protected void notifier(Event event) {}
}