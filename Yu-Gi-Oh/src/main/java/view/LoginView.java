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

        String[] command = input.split(" ");
        try {
            if (input.startsWith("user create")) {
                String username = getStringValueFromCommand("username", command);
                String nickname = getStringValueFromCommand("nickname", command);
                String password = getStringValueFromCommand("password", command);

                if (username == null || nickname == null || password == null)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);
                controller.createUser(username, nickname, password);

                System.out.println("user created successfully!");
            } else if (input.startsWith("user login")) {
                String username = getStringValueFromCommand("username", command);
                String password = getStringValueFromCommand("password", command);

                if (username == null || password == null)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);
                controller.login(username, password);
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
