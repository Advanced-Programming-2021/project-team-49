package controller.effects.spells;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterReborn extends EffectHandler {

    private final List<Card> bothGraveyardsMonsters;

    public MonsterReborn(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        bothGraveyardsMonsters = getBothGraveyardsMonsters();
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
        if (bothGraveyardsMonsters.isEmpty())
            throw new GameErrorException("Both graveyards are empty");
    }

    @Override
    public void action() {
        Card card = selectCardFromList(bothGraveyardsMonsters);

        if (field.getAttackerMat().getCardList(Location.GRAVEYARD).contains(card))
            field.getAttackerMat().removeCard(card, Location.GRAVEYARD);
        else
            field.getDefenderMat().removeCard(card, Location.GRAVEYARD);

        field.getAttackerMat().addCard(card, Location.MONSTER_ZONE);

        moveCardToGraveyard();
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    private List<Card> getBothGraveyardsMonsters() {
        List<Card> bothGraveyardsMonsters = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyardsMonsters.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.GRAVEYARD)) {
            if (card instanceof Monster)
                bothGraveyardsMonsters.add(card);
        }
        return bothGraveyardsMonsters;
    }
}
