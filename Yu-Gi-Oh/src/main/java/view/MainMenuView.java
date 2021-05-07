package view;

import controller.Controller;
import controller.LoginController;
import controller.MainMenuController;

import java.util.Scanner;

public class MainMenuView extends AbstractView {
    public MainMenuView(MainMenuController controller) {
        Scanner inputStream = new Scanner(System.in);
        String input = removeExtraWhitespace(inputStream.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(inputStream.nextLine());
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
                // TODO: implement start game
            } else
                throw new RuntimeException(INVALID_COMMAND_MESSAGE);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }
}
