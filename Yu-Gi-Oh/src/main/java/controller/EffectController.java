package controller;

import model.game.Card;
import model.game.Field;
import model.game.Location;

import java.util.ArrayList;

public class EffectController {

    private final Field field;

    public EffectController(Field field) {
        this.field = field;
    }

    public void monsterReborn(Card card) {
        if (field.getAttackerMat().getGraveyard().contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.SPELL_AND_TRAP_ZONE);
    }

    public ArrayList<Card> getBothGraveyards() {
        ArrayList<Card> bothGraveyards = new ArrayList<>();
        bothGraveyards.addAll(field.getAttackerMat().getGraveyard());
        bothGraveyards.addAll(field.getDefenderMat().getGraveyard());

        return bothGraveyards;
    }
}