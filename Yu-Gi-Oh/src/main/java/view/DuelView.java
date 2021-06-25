package view;

import controller.DuelController;
import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import exception.StopAttackException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.game.Field;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Card;
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

    private static String getCardStringView(Card card) {
        if (card == null) {
            return "E ";
        } else if (card instanceof Monster) {
            if (((Monster) card).isAttacker())
                return "OO";
            else if (card.isFaceUp())
                return "DO";
            else
                return "DH";
        } else if (card.isFaceUp()) {
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

    public static void showCardInfoStringView(Card card) {
        StringBuilder cardInfo = new StringBuilder();
        if (card instanceof Monster) {
            Monster monster = (Monster) card;
            cardInfo.append("Name: ").append(monster.getName()).append("\n")
                    .append("Level: ").append(monster.getLevel()).append("\n")
                    .append("Type: ").append(monster.getMonsterType()).append("\n")
                    .append("ATK: ").append(monster.getBaseAttack()).append("\n")
                    .append("DEF: ").append(monster.getBaseDefense()).append("\n")
                    .append("Description: ").append(monster.getDescription());
        } else {
            SpellTrap spellTrap = (SpellTrap) card;
            cardInfo.append("Name: ").append(spellTrap.getName()).append("\n")
                    .append(spellTrap.getType().getType())
                    .append("Type: ").append(spellTrap.getEffectType()).append("\n")
                    .append("Description: ").append(spellTrap.getDescription());
        }
        System.out.println(cardInfo);
    }

    public static void showCardListStringView(List<Card> list) {
        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            cards.append(i + 1).append(". ").append(list.get(i).getName()).append(": ")
                    .append(list.get(i).getDescription());
            if (list.get(i) instanceof Monster)
                cards.append("\nLevel: ").append(((Monster) list.get(i)).getLevel());
        }
        System.out.println(cards);
    }

    @Override
    public void run() {
        runCommand("next phase");
        super.run();
    }

    public static int selectNumber(int begin, int end) {
        System.out.println("select a card:");

        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());
        if (input.equalsIgnoreCase("cancel"))
            return 0;

        int choice = Integer.parseInt(input);
        if (choice < begin || choice > end) {
            System.out.println("Enter a number from " + begin + " to " + end);
            return -1;
        }
        return choice;
    }

    public static int selectAnOption(String[] options) {
        System.out.println("select:");
        for (int i = 1; i <= options.length; i++)
            System.out.println(i + ". " + options[i - 1]);

        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());
        if (input.equalsIgnoreCase("cancel"))
            return 0;

        int choice = Integer.parseInt(input);
        if (choice < 1 || choice > options.length) {
            System.out.println("Enter a number from 1 to " + options.length);
            return -1;
        }
        return choice;
    }

    public static void showAttackOutcome(boolean attackedDefendingCard, int damage) {
        if (attackedDefendingCard) {
            if (damage > 0)
                System.out.println("your opponent's monster is destroyed and your opponent receives "
                        + damage + " battle damage");
            else if (damage == 0)
                System.out.println("both you and your opponent monster cards are destroyed and no "
                        + "one receives damage");
            else
                System.out.println("Your monster card is destroyed and you received "
                        + (-damage) + " battle damage");
        } else {
            if (damage > 0)
                System.out.println("the defense position monster is destroyed");
            else if (damage == 0)
                System.out.println("no card is destroyed");
            else
                System.out.println("no card is destroyed and you received "
                        + (-damage) + " battle damage");
        }
    }

    public static void showAttackOutcome(String defendingCardName,  int damage) {
        System.out.print("opponent's monster card was " + defendingCardName + " and ");
        showAttackOutcome(true, damage);
    }

    public static void showDirectAttackOutcome(int damage) {
        System.out.println("you opponent receives " + damage + " battle damage");
    }

    private void drawCard() throws EndOfRoundException {
        Card drawnCard = controller.drawCard();
        if (drawnCard != null)
            System.out.println("new card added to hand: " + drawnCard.getName());
    }

    private void beginNextPhase() throws EndOfRoundException {
        controller.changePhase();
        if (controller.getPhaseNumber() == 0) {
            System.out.println("it's " + controller.getCurrentPlayer().getUser().getNickname() + "'s turn");
            System.out.println("phase: " + controller.getPhaseName());
            drawCard();
            return;
        }
        System.out.println("phase: " + controller.getPhaseName());
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.equals("surrender"))
                controller.surrender();
            else if (input.equals("next phase"))
                beginNextPhase();
            else if (input.equals("select -d")) {
                controller.deselectCard();
                System.out.println("card deselected");
            } else if (input.startsWith("select"))
                selectCard(controller, input);
            else if (input.equals("activate effect")) {
                controller.activateSpell();
                System.out.println("spell activated");
            } else if (input.startsWith("summon")) {
                controller.summon();
                System.out.println("summoned successfully");
            } else if (input.equals("flip-summon")) {
                controller.flipSummon();
                System.out.println("flip summoned successfully");
            } else if (input.startsWith("set")) {
                if (isFlagUsedInCommand("position", input)) {
                    String position = getArgument("position", input, "set");
                    controller.setPosition(position);
                    System.out.println("monster card position changed successfully");
                } else if (controller.getSelectedCard() instanceof Monster)
                    controller.setMonster();
                else
                    controller.setSpellOrTrap();
                System.out.println("set successfully");
            } else if (input.startsWith("show graveyard"))
                controller.showGraveyard(isFlagUsedInCommand("opponent", input));
            else if (input.equals("card show --selected"))
                controller.showSelectedCard();
            else if (input.equals("attack direct"))
                controller.directAttack();
            else if (input.startsWith("attack "))
                controller.attack(Integer.parseInt(input.substring(7)));
            else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException|StopAttackException exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("invalid value entered as a number");
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
}