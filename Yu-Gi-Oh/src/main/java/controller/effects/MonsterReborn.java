package controller.effects;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterReborn extends EffectController {

    public MonsterReborn(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        List<Card> bothGraveyards = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyards.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyards.add(card);
        }

        Card card;
        if (bothGraveyards.isEmpty())
            throw new GameErrorException("Both graveyards are empty");
        else
            card = selectCardFromList(bothGraveyards);

        if (field.getAttackerMat().getCardList(Location.GRAVEYARD).contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.SPELL_AND_TRAP_ZONE);
    }
}
