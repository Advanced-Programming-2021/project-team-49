package view;

import controller.DuelController;
import exception.GameErrorException;
import model.game.*;

import java.text.MessageFormat;
import java.util.*;

public class DuelView extends AbstractView {
    private static final Map<String, Location> LOCATION_MAP;
    static {
        LOCATION_MAP = new HashMap<>();

        LOCATION_MAP.put("hand", Location.HAND);
        LOCATION_MAP.put("graveyard", Location.GRAVEYARD);
        LOCATION_MAP.put("monster", Location.MONSTER_ZONE);
        LOCATION_MAP.put("spell", Location.SPELL_AND_TRAP_ZONE);
        LOCATION_MAP.put("field", Location.FIELD_ZONE);
    }

    public DuelView(DuelController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private static boolean runCommand(DuelController controller, String input) {
        try {
            if (input.equals("select -d")) {
                controller.deselectCard();
                System.out.println("card deselected");
            } else if (input.startsWith("select")) {
                selectCard(controller, input);
            } else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("invalid number entered");
        }
        return true;
    }

    private static void selectCard(DuelController controller, String input) {
        String[] locationFlags = {"hand", "monster", "spell", "field"};
        boolean[] isFlagFound = findFlags(locationFlags, input);
        int locationFlagIndex = getFlagIndex(isFlagFound);

        int position = 0;
        boolean opponent = isFlagUsedInCommand("opponent", input);

        if (locationFlags[locationFlagIndex].equals("field")) {
            if ((opponent && input.split(" ").length > 3)
                    || input.split(" ").length > 2)
                throw new GameErrorException("invalid selection");
        } else {
            String positionString;
            if (opponent)
                positionString = getArgument("opponent", locationFlags[locationFlagIndex], input, "select");
            else
                positionString = getArgument(locationFlags[locationFlagIndex], input, "select");
            position = Integer.parseInt(positionString);
        }
        controller.selectCard(LOCATION_MAP.get(locationFlags[locationFlagIndex]), position,
                isFlagUsedInCommand("opponent", input));

        System.out.println("card selected");
    }

    private static int getFlagIndex(boolean[] isFlagFound) {
        int locationFlagIndex = 0;

        while (locationFlagIndex < isFlagFound.length) {
            if (isFlagFound[locationFlagIndex])
                break;
            locationFlagIndex++;
        }

        if (locationFlagIndex == isFlagFound.length)
            throw new GameErrorException("invalid selection");
        for (int i = locationFlagIndex + 1; i < isFlagFound.length; i++)
            if (isFlagFound[i])
                throw new GameErrorException("invalid selection");

        return locationFlagIndex;
    }

    private String getFieldStringView(Field field) {
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
                    + "\n  " + getZoneStringView(gameMat, Location.SPELL_AND_TRAP_ZONE, flippedZoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Location.MONSTER_ZONE, flippedZoneViewOrder)
                    + "\n" + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.GRAVEYARD))
                    + offsetString + getCardStringView(gameMat.getCard(Location.FIELD_ZONE));
        } else {
            int[] zoneViewOrder = {5, 3, 1, 2, 4};
            return getCardStringView(gameMat.getCard(Location.FIELD_ZONE)) + offsetString
                    + MessageFormat.format("{:02d}", gameMat.getCardCount(Location.GRAVEYARD))
                    + "\n  " + getZoneStringView(gameMat, Location.MONSTER_ZONE, zoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Location.SPELL_AND_TRAP_ZONE, zoneViewOrder)
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

    private String getZoneStringView(GameMat gameMat, Location zone, int[] viewOrder) {
        StringBuilder stringViewBuilder = new StringBuilder();
        for (int position : viewOrder)
            stringViewBuilder.append("\t").append(getCardStringView(gameMat.getCard(zone, position)));
        return stringViewBuilder.toString();
    }
}
