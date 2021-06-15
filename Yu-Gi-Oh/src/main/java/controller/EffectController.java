package controller;

import exception.GameErrorException;
import model.cardtemplate.Card;
import model.game.Field;
import model.game.Location;
import model.user.Deck;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class EffectController {

    private final Field field;

    public EffectController(Field field) {
        this.field = field;
    }

    public void monsterReborn() {
        Card card;
        List<Card> bothGraveyards = new ArrayList<>();
        bothGraveyards.addAll(field.getAttackerMat().getCardList(Location.GRAVEYARD));
        bothGraveyards.addAll(field.getDefenderMat().getCardList(Location.GRAVEYARD));

        if (bothGraveyards.isEmpty())
            throw new GameErrorException("Both graveyards are empty");
        else {
            DuelView.showCardListStringView(bothGraveyards);

            int selected;
            do {
                selected = DuelView.selectNumber(1, bothGraveyards.size());
            } while (selected == -1);
            card = bothGraveyards.get(selected - 1);
        }

        if (field.getAttackerMat().getCardList(Location.GRAVEYARD).contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.SPELL_AND_TRAP_ZONE);
    }

    public void terraforming() {
        Deck deck;
        

    }

}