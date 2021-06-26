package view;

import controller.DuelController;
import exception.*;
import model.cardtemplate.CardTemplate;
import model.game.Field;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;
import model.game.card.SpellTrap;

import java.text.MessageFormat;
import java.util.List;

public class DuelView extends AbstractView {

    private final DuelController controller;

    public DuelView(DuelController controller) {
        this.controller = controller;
    }

    private static void selectCard(DuelController controller, String input) {
        boolean opponent = isFlagUsedInCommand("opponent", input);

        if (isFlagUsedInCommand("monster", input))
            controller.selectCard(Location.MONSTER_ZONE,
                    Integer.parseInt(getArgument("hand", input, "select")),
                    opponent);
        else if (isFlagUsedInCommand("spell", input))
            controller.selectCard(Location.SPELL_AND_TRAP_ZONE,
                    Integer.parseInt(getArgument("hand", input, "select")),
                    opponent);
        else if (isFlagUsedInCommand("field", input))
            controller.selectCard(Location.FIELD_ZONE, 1, opponent);
        else if (isFlagUsedInCommand("hand", input))
            controller.selectCard(Location.HAND,
                    Integer.parseInt(getArgument("hand", input, "select")),
                    opponent);
        else
            throw new GameErrorException("invalid selection");

        System.out.println("card selected");
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
            return gameMat.getPlayer().getUser().getNickname() + ": " + gameMat.getPlayer().getLifePoints()
                    + "\n  \t" + handString
                    + "\n" + MessageFormat.format("{0, number}", gameMat.getCardCount(Location.DECK))
                    + "\n  " + getZoneStringView(gameMat, Location.SPELL_AND_TRAP_ZONE, flippedZoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Location.MONSTER_ZONE, flippedZoneViewOrder)
                    + "\n" + MessageFormat.format("{0, number}", gameMat.getCardCount(Location.GRAVEYARD))
                    + offsetString + getCardStringView(gameMat.getCard(Location.FIELD_ZONE));
        } else {
            int[] zoneViewOrder = {5, 3, 1, 2, 4};
            return getCardStringView(gameMat.getCard(Location.FIELD_ZONE)) + offsetString
                    + MessageFormat.format("{0, number}", gameMat.getCardCount(Location.GRAVEYARD))
                    + "\n  " + getZoneStringView(gameMat, Location.MONSTER_ZONE, zoneViewOrder)
                    + "\n  " + getZoneStringView(gameMat, Location.SPELL_AND_TRAP_ZONE, zoneViewOrder)
                    + "\n  " + offsetString
                    + MessageFormat.format("{0, number}", gameMat.getCardCount(Location.DECK))
                    + "\n  \t" + handString
                    + "\n" + gameMat.getPlayer().getUser().getNickname() + ": " + gameMat.getPlayer().getLifePoints();
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
        for (int position : viewOrder) {
            if (position <= gameMat.getCardCount(zone))
                stringViewBuilder.append("\t").append(getCardStringView(gameMat.getCard(zone, position)));
            else
                stringViewBuilder.append("\t").append("E ");
        }
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
                    .append(spellTrap.getType().getType()).append("\n")
                    .append("Type: ").append(spellTrap.getEffectType()).append("\n")
                    .append("Description: ").append(spellTrap.getDescription());
        }
        System.out.println(cardInfo);
    }

    public static void showCardListStringView(List<Card> list) {
        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            cards.append(i + 1).append(". ").append(list.get(i).getName()).append(": ")
                    .append(list.get(i).getDescription()).append("\n");
            if (list.get(i) instanceof Monster)
                cards.append("Level: ").append(((Monster) list.get(i)).getLevel()).append("\n");
        }
        System.out.print(cards);
    }

    public static void showCardNameListStringView(List<CardTemplate> list) {
        StringBuilder cards = new StringBuilder();

        for (int i = 0; i < list.size(); i++)
            cards.append(i + 1).append(". ").append(list.get(i).getName()).append("\n");
        System.out.print(cards);
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
            beginNextPhase();
            beginNextPhase();
            return;
        }
        System.out.println("phase: " + controller.getPhaseName());
    }

    @Override
    public void run() {
        runCommand("next phase");
        super.run();
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.equals("show field"))
                System.out.println(getFieldStringView(controller.getField()));
            else if (input.equals("surrender"))
                controller.surrender();
            else if (input.equals("next phase")) {
                beginNextPhase();
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.equals("select -d")) {
                controller.deselectCard();
                System.out.println("card deselected");
            } else if (input.startsWith("select"))
                selectCard(controller, input);
            else if (input.equals("activate effect")) {
                controller.activateSpell();
                System.out.println("spell activated");
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.startsWith("summon")) {
                controller.summon();
                System.out.println("summoned successfully");
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.equals("flip-summon")) {
                controller.flipSummon();
                System.out.println("flip summoned successfully");
                System.out.println(getFieldStringView(controller.getField()));
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
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.startsWith("show graveyard"))
                controller.showGraveyard(isFlagUsedInCommand("opponent", input));
            else if (input.startsWith("card show ")) {
                if (isFlagUsedInCommand("selected", input))
                    controller.showSelectedCard();
                else
                    throw new GameErrorException(INVALID_COMMAND_MESSAGE);
            } else if (input.equals("attack direct")) {
                controller.directAttack();
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.startsWith("attack ")) {
                controller.attack(Integer.parseInt(input.substring(7)));
                System.out.println(getFieldStringView(controller.getField()));
            } else if (input.startsWith("increase "))
                controller.increaseLifePoints(Integer.parseInt(getArgument("LP", input, "increase ")));
            else if (input.startsWith("duel set-winner ")) {
                controller.forceWinner(input.substring("duel set-winner ".length()));
            } else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException|StopAttackException exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("invalid value entered as a number");
        } catch (EndPhaseException exception) {
            System.out.println(exception.getMessage());
            beginNextPhase();
        } catch (EndOfMatchException exception) {
            System.out.println(exception.getWinner().getUser().getUsername() + " won the whole match with score: "
                    + exception.getWinner().getWinCount() + "-" + exception.getLoser().getWinCount());
            controller.escape();
            return false;
        } catch (EndOfRoundException exception) {
            System.out.println(exception.getWinner().getUser().getUsername() + " won the game and the score is: "
                    + exception.getWinner().getWinCount() + "-" + exception.getLoser().getWinCount());
        }
        return true;
    }
}