package view;

import controller.DuelController;
import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.cardtemplate.SpellTrapCard;
import model.cardtemplate.Type;
import model.game.Field;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Castable;
import model.game.card.Monster;
import model.game.card.SpellTrap;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuelView extends AbstractView {

    private static final Map<String, Location> LOCATION_MAP;
    private final DuelController controller;

    static {
        LOCATION_MAP = new HashMap<>();

        LOCATION_MAP.put("hand", Location.HAND);
        LOCATION_MAP.put("graveyard", Location.GRAVEYARD);
        LOCATION_MAP.put("monster", Location.MONSTER_ZONE);
        LOCATION_MAP.put("spell", Location.SPELL_AND_TRAP_ZONE);
        LOCATION_MAP.put("field", Location.FIELD_ZONE);
    }

    public DuelView(DuelController controller) {
        this.controller = controller;
    }

    private static void beginNextPhase(DuelController controller) throws EndOfRoundException {
        controller.changePhase();
        if (controller.getPhaseNumber() == 0) {
            System.out.println("it's " + controller.getCurrentPlayer().getUser().getNickname() + "'s turn");
            System.out.println("phase: " + controller.getPhaseName());
            drawCard(controller);
            return;
        }
        System.out.println("phase: " + controller.getPhaseName());
    }

    private static void drawCard(DuelController controller) throws EndOfRoundException {
        CardTemplate drawnCardTemplate = controller.drawCard();
        System.out.println("new card added to hand: " + drawnCardTemplate.getName());
    }

    private static void selectCard(DuelController controller, String input) {
        String[] locationFlags = {"hand", "monster", "spell", "field"};
        boolean[] isFlagFound = findFlags(locationFlags, input);
        int locationFlagIndex = getFlagIndex(isFlagFound);

        int position = 0;
        boolean opponent = isFlagUsedInCommand("opponent", input);

        if (locationFlags[locationFlagIndex].equals("field")) {
            if ((opponent && input.split(" ").length > 3) || input.split(" ").length > 2)
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

    private static String getFieldStringView(Field field) {
        return getGameMatStringView(field.getDefenderMat(), true)
                + "\n\n--------------------------\n\n"
                + getGameMatStringView(field.getAttackerMat(), false);
    }

    private static String getGameMatStringView(GameMat gameMat, boolean isFlipped) {
        StringBuilder handString = new StringBuilder();
        StringBuilder offsetString = new StringBuilder("\t");

        for (int i = 0; i < gameMat.getCardCount(Location.HAND); i++)
            handString.append("c \t");
        for (int i = 0; i < 5; i++)
            offsetString.append("  \t");

        if (isFlipped) {
            int[] flippedZoneViewOrder = {4, 2, 1, 3, 5};
            return gameMat.getPlayer().getUser().getNickname() + ":" + gameMat.getPlayer().getLifePoints()
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
                    + "\n" + gameMat.getPlayer().getUser().getNickname() + ":" + gameMat.getPlayer().getLifePoints();
        }
    }

    private static String getCardStringView(CardTemplate cardTemplate) {
        if (cardTemplate == null) {
            return "E ";
        } else if (cardTemplate instanceof MonsterCard) {
            if (((Monster) cardTemplate).isAttacker())
                return "OO";
            else if (((Monster) cardTemplate).isFaceUp())
                return "DO";
            else
                return "DH";
        } else if (((Castable) cardTemplate).isFaceUp()) {
            return "O ";
        } else
            return "H ";
    }

    private static String getZoneStringView(GameMat gameMat, Location zone, int[] viewOrder) {
        StringBuilder stringViewBuilder = new StringBuilder();
        for (int position : viewOrder)
            stringViewBuilder.append("\t").append(getCardStringView(gameMat.getCard(zone, position)));
        return stringViewBuilder.toString();
    }

    public static void showCardInfoStringView(CardTemplate cardTemplate) {
        StringBuilder cardInfo = new StringBuilder();
        if (cardTemplate instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) cardTemplate;
            cardInfo.append("Name: ").append(monsterCard.getName()).append("\n")
                    .append("Level: ").append(monsterCard.getLevel()).append("\n")
                    .append("Type: ").append(monsterCard.getMonsterType()).append("\n")
                    .append("ATK: ").append(monsterCard.getBaseAttack()).append("\n")
                    .append("DEF: ").append(monsterCard.getBaseDefence()).append("\n")
                    .append("Description: ").append(monsterCard.getDescription());
        } else if (((SpellTrapCard) cardTemplate).getType() == Type.SPELL) {
            SpellTrapCard spellCard = (SpellTrapCard) cardTemplate;
            cardInfo.append("Name: ").append(spellCard.getName()).append("\n")
                    .append("SpellTrapCard\n")
                    .append("Type: ").append(spellCard.getEffectType()).append("\n")
                    .append("Description: ").append(spellCard.getDescription());
        } else {
            SpellTrapCard trapCard = (SpellTrapCard) cardTemplate;
            cardInfo.append("Name: ").append(trapCard.getName()).append("\n")
                    .append("TrapCard\n")
                    .append("Type: ").append(trapCard.getEffectType()).append("\n")
                    .append("Description: ").append(trapCard.getDescription());
        }
        System.out.println(cardInfo);
    }

    public static void showCardListStringView(List<CardTemplate> list) {
        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            cards.append(i + 1).append(". ").append(list.get(i).getName()).append(": ")
                    .append(list.get(i).getDescription()).append("\n");

        System.out.println(cards);
    }

    @Override
    public void run() {
        runCommand("next phase");
        super.run();
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.equals("surrender"))
                controller.surrender();
            else if (input.equals("next phase"))
                beginNextPhase(controller);
            else if (input.equals("select -d")) {
                controller.deselectCard();
                System.out.println("card deselected");
            } else if (input.startsWith("select"))
                selectCard(controller, input);
            else if (input.equals("activate spell"))
                controller.activateSpell();
            else if (input.startsWith("summon")) {
                controller.summon();
                System.out.println("summoned successfully");
            } else if (input.startsWith("set")) {
                if (isFlagUsedInCommand("position", input)) {
                    String position = getArgument("position", input, "set");
                    controller.setPosition(position);
                    System.out.println("monster card position changed successfully");
                } else if (controller.getSelectedCard() instanceof MonsterCard)
                    controller.setMonster();
                else
                    controller.setSpellOrTrap();
                System.out.println("set successfully");
            } else if (input.startsWith("show graveyard"))
                controller.showGraveyard(isFlagUsedInCommand("opponent", input));
            else if (input.equals("card show --selected")) {
                controller.showSelectedCard();
            } else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("invalid number entered");
        } catch (EndOfMatchException exception) {
            System.out.println(exception.getWinner().getUser().getUsername() + " won the whole match with score: "
                    + exception.getWinner().getWinCount() + "-" + exception.getLoser().getWinCount());
            return false;
        } catch (EndOfRoundException exception) {
            System.out.println(exception.getWinner().getUser().getUsername() + " won the game and the score is: "
                    + exception.getWinner().getWinCount() + "-" + exception.getLoser().getWinCount());
        }
        return true;
    }

    public static int selectNumber(int begin, int end) {
        System.out.println("select a card:");
        int choice;
        try {
            choice = Integer.parseInt(removeExtraWhitespace(INPUT_STREAM.nextLine()));

            if (choice < begin || choice > end)
                throw new GameErrorException();
        } catch (Exception e) {
            System.out.println("Enter a number from " + begin + " to " + end);
            return -1;
        }
        return choice;
    }
}