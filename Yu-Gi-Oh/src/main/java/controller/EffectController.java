package controller;

import exception.EndOfRoundException;
import exception.GameErrorException;
import model.cardtemplate.*;
import model.game.Field;
import model.game.Location;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class EffectController {

    private final Field field;
    private final DuelController controller;

    public EffectController(Field field, DuelController controller) {
        this.field = field;
        this.controller = controller;
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
        Card selectedSpell;
        List<Card> cardsOfDeck;
        List<Card> spellsOfDeck = new ArrayList<>();
        cardsOfDeck = field.getAttackerMat().getCardList(Location.DECK);
        for (Card card : cardsOfDeck) {
            if (card instanceof SpellTrapCard) {
                if (((SpellTrapCard) card).getType().equals(Type.SPELL) &&
                        ((SpellTrapCard) card).getEffectType().equals(EffectType.FIELD)) spellsOfDeck.add(card);
            }
        }
        if (spellsOfDeck.isEmpty())
            throw new GameErrorException("You don't have any spell");
        else {
            DuelView.showCardListStringView(spellsOfDeck);
            int selected;
            do {
                selected = DuelView.selectNumber(1,spellsOfDeck.size());
                if (selected == 0)
                    throw new GameErrorException("cancelled");
            } while (selected == -1);
            selectedSpell = spellsOfDeck.get(selected - 1);
            field.getAttackerMat().addCard(selectedSpell , Location.HAND);
            field.getAttackerMat().removeCard(selectedSpell , Location.DECK);
        }
    }

    public void potOfGreed() {
        controller.drawCard();
        controller.drawCard();
    }


}