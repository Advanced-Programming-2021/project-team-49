package controller;

import exception.GameErrorException;
import model.cardtemplate.Card;
import model.cardtemplate.MonsterCard;
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
        List<Card> bothGraveyards = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof MonsterCard)
                bothGraveyards.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof MonsterCard)
                bothGraveyards.add(card);
        }

        Card card;
        if (bothGraveyards.isEmpty())
            throw new GameErrorException("Both graveyards are empty");
        else {
            DuelView.showCardListStringView(bothGraveyards);

            int selected;
            do {
                selected = DuelView.selectNumber(1, bothGraveyards.size());
                if (selected == 0)
                    throw new GameErrorException("cancelled");
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