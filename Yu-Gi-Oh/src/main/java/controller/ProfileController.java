package controller;

import model.user.User;

public class ProfileController extends AbstractController {
    public static final String TITLE = "Profile Menu";
    private final MasterController masterController;

    public ProfileController(MasterController masterController, User user) {
        this.masterController = masterController;
    }

    public void run() {

    }
}
