package controller.effects.monsters;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import exception.StopAttackException;
import model.cardtemplate.MonsterType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

import java.util.ArrayList;
import java.util.List;

public class Texchanger extends EffectHandler {

    private boolean isEffectUsed = false;
    private final List<Card> cyberseCards;

    public Texchanger(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        this.cyberseCards = getCyberseCards();
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        if (!isEffectUsed) {
            if (cyberseCards.isEmpty())
                throw new GameErrorException("You don't have any cyberse card");
            Card card = selectCardFromList(cyberseCards);
            if (field.getDefenderMat().getCardList(Location.GRAVEYARD).contains(card))
                field.getDefenderMat().moveCard(Location.GRAVEYARD, card, Location.MONSTER_ZONE);
            else if (field.getDefenderMat().getCardList(Location.DECK).contains(card))
                field.getDefenderMat().moveCard(Location.DECK, card, Location.MONSTER_ZONE);
            else if (field.getDefenderMat().getCardList(Location.HAND).contains(card))
                field.getDefenderMat().moveCard(Location.HAND, card, Location.MONSTER_ZONE);
            throw new StopAttackException("The attack is neutralized based on the effect of Texchanger card");
        } else throw new GameErrorException("The effect is used already in this turn");
    }

    @Override
    public void notifier(Event event) {
        if (event == Event.END_TURN)
            isEffectUsed = false;
    }

    @Override
    public void deActivate() {

    }

    private List<Card> getCyberseCards() {
        List<Card> cyberseCards = new ArrayList<>();
        for (Card card : field.getDefenderMat().getCardList(Location.GRAVEYARD)) {
            if ( card instanceof Monster && ((Monster) card).getMonsterType().equals(MonsterType.CYBERSE))
                cyberseCards.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.DECK)) {
            if ( card instanceof Monster && ((Monster) card).getMonsterType().equals(MonsterType.CYBERSE))
                cyberseCards.add(card);
        }
        for (Card card : field.getDefenderMat().getCardList(Location.DECK)) {
            if ( card instanceof Monster && ((Monster) card).getMonsterType().equals(MonsterType.CYBERSE))
                cyberseCards.add(card);
        }
        return cyberseCards;
    }
}
