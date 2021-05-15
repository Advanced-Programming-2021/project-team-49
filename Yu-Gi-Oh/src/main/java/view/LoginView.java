package view;

import controller.LoginController;

public class LoginView extends AbstractView {
    public LoginView(LoginController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private static boolean runCommand(LoginController controller, String input) {
        boolean isMenuOpen = runDefaultCommands(LoginController.TITLE, input);
        if (!isMenuOpen) {
            controller.escape();
            return false;
        }

        try {
            if (input.startsWith("user create")) {
                String[] argumentNames = {"username", "nickname", "password"};
                String[] arguments = getArguments(argumentNames, null, input, "user create");

                controller.createUser(arguments[0], arguments[1], arguments[2]);
                System.out.println("user created successfully!");
            } else if (input.startsWith("user login")) {
                String[] argumentNames = {"username", "password"};
                String[] arguments = getArguments(argumentNames, null, input, "user login");

                controller.login(arguments[0], arguments[1]);
                isMenuOpen = false;
                System.out.println("user logged in successfully!");
            } else
                throw new RuntimeException(INVALID_COMMAND_MESSAGE);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
        return isMenuOpen;
    }
}
