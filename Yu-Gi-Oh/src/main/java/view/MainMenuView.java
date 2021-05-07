package view;

import controller.MainMenuController;

public class MainMenuView extends AbstractView {
    public MainMenuView(MainMenuController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private boolean runCommand(MainMenuController controller, String input) {
        String[] command = input.split(" ");
        try {
            if (input.equals("menu show-current")) {
                System.out.println(MainMenuController.TITLE);
                return false;
            } else if (input.startsWith("menu enter"))
                controller.enterMenu(input.substring(11));
            else if (input.equals("user logout"))
                controller.escape();
            else if (input.startsWith("duel")) {
                if (!isFlagUsedInCommand("new", command))
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);

                String secondPlayerUsername = getStringValueFromCommand("second-player", command);
                String roundsString = getStringValueFromCommand("rounds", command);

                if (secondPlayerUsername == null || roundsString == null)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);

                int rounds = Integer.parseInt(roundsString);
                controller.startDuel(secondPlayerUsername, rounds);
            } else
                throw new RuntimeException(INVALID_COMMAND_MESSAGE);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}
