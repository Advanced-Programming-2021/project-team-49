package view;

import controller.MainMenuController;
import exception.GameErrorException;

public class MainMenuView extends AbstractView {

    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.equals("menu show-current")) {
                System.out.println(controller.getTitle());
                return false;
            } else if (input.startsWith("menu enter "))
                controller.enterMenu(input.substring(11));
            else if (input.equals("user logout"))
                controller.escape();
            else if (input.startsWith("duel")) {
                startDuel(input);
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

    private void startDuel(String input) {
        String[] flags = {"new", "ai"};
        boolean[] isFlagFound = findFlags(flags, input);

        if (!isFlagFound[0]) {
            System.out.println("hey");
            throw new GameErrorException(INVALID_COMMAND_MESSAGE);
        }else if (isFlagFound[1]) {
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
