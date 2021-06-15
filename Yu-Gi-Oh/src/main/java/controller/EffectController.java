package controller;

import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.game.Field;
import model.game.Location;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class EffectController {

    private final Field field;

    public EffectController(Field field) {
        this.field = field;
    }

    public void monsterReborn() {
        CardTemplate cardTemplate;
        List<CardTemplate> bothGraveyards = new ArrayList<>();
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
            cardTemplate = bothGraveyards.get(selected - 1);
        }

        if (field.getAttackerMat().getCardList(Location.GRAVEYARD).contains(cardTemplate))
            field.getAttackerMat().removeCard(cardTemplate, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(cardTemplate, Location.GRAVEYARD);

        field.getAttackerMat().addCard(cardTemplate, Location.SPELL_AND_TRAP_ZONE);
    }
}