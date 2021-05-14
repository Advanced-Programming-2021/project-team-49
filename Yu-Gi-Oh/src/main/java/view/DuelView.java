package view;

import controller.DuelController;
import model.game.*;

import java.text.MessageFormat;

public class DuelView extends AbstractView {
    public DuelView(DuelController controller) {

    }

    private String fieldStringView(Field field) {
        return getGameMatStringView(field.getDefenderMat(), true)
                + "\n\n--------------------------\n\n"
                + getGameMatStringView(field.getAttackerMat(), false);
    }

    private String getGameMatStringView(GameMat gameMat, boolean isFlipped) {
        StringBuilder handString = new StringBuilder();
        StringBuilder offsetString = new StringBuilder("\t");

        for (int i = 0; i < gameMat.getCardCount(Location.HAND); i++)
            handString.append("c \t");
        for (int i = 0; i < 5; i++)
            offsetString.append("  \t");

        if (isFlipped) {
            int[] flippedZoneViewOrder = {4, 2, 1, 3, 5};
            return gameMat.getPlayer().getNickname() + ":" + gameMat.getLifePoints()
                    + "\n  \t" + handString
                    + "\n" + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.DECK))
                    + "\n  " + getZoneStringView(gameMat, Zone.SPELL_AND_TRAP, flippedZoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Zone.MONSTER, flippedZoneViewOrder)
                    + "\n" + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.GRAVEYARD))
                    + offsetString + getCardStringView(gameMat.getCard(Location.FIELD_ZONE));
        } else {
            int[] zoneViewOrder = {5, 3, 1, 2, 4};
            return getCardStringView(gameMat.getCard(Location.FIELD_ZONE)) + offsetString
                    + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.GRAVEYARD))
                    + "\n  " + getZoneStringView(gameMat, Zone.MONSTER, zoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Zone.SPELL_AND_TRAP, zoneViewOrder)
                    + "\n" + "  " + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.DECK))
                    + "\n" + handString
                    + "\n" + gameMat.getPlayer().getNickname() + ":" + gameMat.getLifePoints();
        }
    }

    private String getCardStringView(Card card) {
        if (card == null)
            return "E ";
        else if (!card.isAttackPossible())
            return "O ";
        else if (card.isAttacker())
            return "OO";
        else if (card.isFaceUp())
            return "DO";
        else
            return "DH";
    }

    private String getZoneStringView(GameMat gameMat, Zone zone, int[] viewOrder) {
        StringBuilder stringViewBuilder = new StringBuilder();
        for (int position : viewOrder)
            stringViewBuilder.append("\t").append(getCardStringView(gameMat.getCard(zone, position)));
        return stringViewBuilder.toString();
    }
}
