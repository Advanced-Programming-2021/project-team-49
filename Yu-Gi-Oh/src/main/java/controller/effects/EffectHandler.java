package controller.effects;

import controller.DuelController;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public abstract class EffectHandler {

    protected final int speed;
    protected final Card card;
    protected final Field field;
    protected final DuelController controller;

    public EffectHandler(int speed, Card card, Field field, DuelController controller) {
        this.speed = speed;
        this.card = card;
        this.field = field;
        this.controller = controller;
    }

    public Card getCard() {
        return card;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Card> selectCardFromList(String sentence, List<Card> cardsToSelect, int selectCount) {
        List<Card> selectedCards = new ArrayList<>();
        field.getAttackerMat().getDuelView().selectCardFromList(sentence, selectCount,
                cardsToSelect, selectedCards::addAll
        );
        return selectedCards;
    }


    public void moveCardToGraveyard() {
        field.getAttackerMat().moveCard(Location.SPELL_AND_TRAP_ZONE, card, Location.GRAVEYARD);
        controller.setSelectedCardLocation(null);
    }

    public List<Card> getBothMonsterZones() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(field.getAttackerMat().getCardList(Location.MONSTER_ZONE));
        cards.addAll(field.getDefenderMat().getCardList(Location.MONSTER_ZONE));
        return cards;
    }

    public boolean askForActivation() {
        final String[] selected = new String[1];
        List<String> options = new ArrayList<>();
        options.add("Activate");
        options.add("Don't Activate");
        field.getDefenderMat().getDuelView().selectAnOption(
                "Do you want to activate \"" + card.getName() + "\" effect?!",
                options, selectedOption -> {
                    for (String option : options) {
                        if (selectedOption.equals(option))
                            selected[0] = option;
                    }
                });

        return selected[0].equals("Activate");
    }

    public boolean canBeActivated(Event event) {
     return true;
    }

    public abstract void activationRequirement();

    public abstract void action();

    public abstract void notifier(Event event);

    public abstract void deActivate();
}