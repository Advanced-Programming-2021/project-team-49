package view;

import controller.MainMenuController;
import exception.GameErrorException;

public class MainMenuView extends AbstractView {
    public MainMenuView(MainMenuController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private boolean runCommand(MainMenuController controller, String input) {
        try {
            if (input.equals("menu show-current")) {
                System.out.println(controller.getTitle());
                return false;
            } else if (input.startsWith("menu enter "))
                controller.enterMenu(input.substring(11));
            else if (input.equals("user logout"))
                controller.escape();
            else if (input.startsWith("duel")) {
                startDuel(controller, input);
            } else
                throw new GameErrorException(INVALID_COMMAND_MESSAGE);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
            return true;
        } catch (NumberFormatException exception) {
            System.out.println("invalid number entered");
            return true;
        }
        return false;
    }

    private void startDuel(MainMenuController controller, String input) {
        String[] flags = {"new", "ai"};
        boolean[] isFlagFound = findFlags(flags, input);

        if (!isFlagFound[0])
            throw new GameErrorException(INVALID_COMMAND_MESSAGE);
        else if (isFlagFound[1]) {
            String roundString = getArgument("rounds", flags, input, "duel");
            int rounds = Integer.parseInt(roundString);

            controller.startAIDuel(rounds);
        } else {
            String[] arguments = getArguments(new String[] {"second-player", "rounds"},
                    flags, input, "duel");
            int rounds = Integer.parseInt(arguments[1]);

            controller.startPlayerDuel(arguments[0], rounds);
        }
    }
}
