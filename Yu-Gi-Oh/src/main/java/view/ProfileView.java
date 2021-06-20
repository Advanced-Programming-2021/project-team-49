package view;

import controller.ProfileController;
import exception.GameErrorException;

public class ProfileView extends AbstractView {

    private final ProfileController controller;

    public ProfileView(ProfileController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.startsWith("profile change ")) {
                if (isFlagUsedInCommand(input, "password")) {
                    String[] arguments = getArguments(new String[] {"current", "new"}, "password", input,
                            "profile change");

                    controller.changePassword(arguments[0], arguments[1]);
                    System.out.println("password changed successfully!");
                } else {
                    String newNickname = getArgument("nickname", input, "profile change");

                    controller.changeNickname(newNickname);
                    System.out.println("nickname changed successfully!");
                }
            } else
                runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
