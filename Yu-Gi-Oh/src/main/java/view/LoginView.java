package view;

import controller.LoginController;
import exception.YugiohException;

public class LoginView extends AbstractView {
    public LoginView(LoginController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private static boolean runCommand(LoginController controller, String input) {
        try {
            if (input.startsWith("user create")) {
                String[] arguments = getArguments(new String[] {"username", "nickname", "password"}, null,
                        input, "user create");

                controller.createUser(arguments[0], arguments[1], arguments[2]);
                System.out.println("user created successfully!");
            } else if (input.startsWith("user login")) {
                String[] arguments = getArguments(new String[] {"username", "password"}, null, input, "user login");

                controller.login(arguments[0], arguments[1]);
                System.out.println("user logged in successfully!");
                return false;
            } else
                return runDefaultCommands(input, controller);
        } catch (YugiohException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
