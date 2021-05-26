package view;

import controller.ProfileController;

public class ProfileView extends AbstractView {

    private final ProfileController controller;

    public ProfileView(ProfileController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        return true;
    }
}
