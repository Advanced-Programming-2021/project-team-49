package view;

import controller.LoginController;
import exception.GameErrorException;

public class LoginView extends AbstractView {

    private final LoginController controller;

    public LoginView(LoginController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.startsWith("user create")) {
                String[] arguments = getArguments(new String[]{"username", "nickname", "password"},
                        input, "user create");

                controller.createUser(arguments[0], arguments[1], arguments[2]);
                System.out.println("user created successfully!");
            } else if (input.startsWith("user login")) {
                String[] arguments = getArguments(new String[]{"username", "password"}, input, "user login");

                controller.login(arguments[0], arguments[1]);
                System.out.println("user logged in successfully!");
                return false;
            } else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
