package controller;

import model.user.User;

public class ProfileController extends AbstractController {

    public ProfileController(MasterController masterController, User user) {
        super(masterController, user);
        title = "Main Menu";
    }

    public void run() {

    }
}
