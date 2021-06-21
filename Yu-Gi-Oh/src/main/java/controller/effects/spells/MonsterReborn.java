package controller.effects.spells;

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

    public MonsterReborn(Card card, Field field, DuelController controller) {
        super(card, field, controller);
    }

    @Override
    public void action() {
        if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");

        List<Card> bothGraveyards = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyards.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyards.add(card);
        }

        if (bothGraveyards.isEmpty())
            throw new GameErrorException("Both graveyards are empty");

        Card card = selectCardFromList(bothGraveyards);

        if (field.getAttackerMat().getCardList(Location.GRAVEYARD).contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.MONSTER_ZONE);

        moveCardToGraveyard();
    }
}
