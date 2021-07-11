package controller.effects.monsters;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class BeastKingBarbaros extends EffectHandler {

    private final List<Card> monsterZone;
    private boolean specialSummoned;

    public BeastKingBarbaros(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        monsterZone = field.getAttackerMat().getCardList(Location.MONSTER_ZONE);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {
        if (monsterZone.size() < 3)
            throw new GameErrorException();
    }

    @Override
    public void action() {

        final String[] selected = new String[1];
        List<String> options = new ArrayList<>();
        options.add("Normal summon/set without tributing (original ATK becomes 1900)");
        options.add("Tribute 3 monsters to tribute summon (all opponent monsters will be destroyed)");
        field.getAttackerMat().getDuelView().selectAnOption(
                "Select:", options, selectedOption -> {
                    for (String option : options) {
                        if (selectedOption.equals(option))
                            selected[0] = option;
                    }
                });

        if (selected[0].startsWith("Normal")) {
            specialSummoned = false;
            ((Monster) card).decreaseAttack(1100);
            return;
        }

        List<Card> selectedCards = select3CardsFromList(monsterZone);

        for (Card selectedCard : selectedCards)
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selectedCard, Location.GRAVEYARD);

        field.getAttackerMat().moveCard(Location.HAND, card, Location.MONSTER_ZONE);

        specialSummoned = true;
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public boolean isSpecialSummoned() {
        return specialSummoned;
    }
}
