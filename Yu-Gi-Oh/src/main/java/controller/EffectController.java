package controller;

import exception.GameErrorException;
import model.game.Card;
import model.game.Field;
import model.game.Location;
import view.DuelView;

import java.util.ArrayList;

public class EffectController {

    private final Field field;

    public EffectController(Field field) {
        this.field = field;
    }

    public void monsterReborn() {
        Card card;
        ArrayList<Card> bothGraveyards = new ArrayList<>();
        bothGraveyards.addAll(field.getAttackerMat().getLocationList(Location.GRAVEYARD));
        bothGraveyards.addAll(field.getDefenderMat().getLocationList(Location.GRAVEYARD));

        if (bothGraveyards.size() == 0)
            throw new GameErrorException("Both graveyards are empty");
        else {
            DuelView.showCardListStringView(bothGraveyards);

            int selected;
            do {
                selected = DuelView.selectNumber(1, bothGraveyards.size());
            } while (selected == -1);
            card = bothGraveyards.get(selected - 1);
        }

        if (field.getAttackerMat().getLocationList(Location.GRAVEYARD).contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.SPELL_AND_TRAP_ZONE);
    }
}