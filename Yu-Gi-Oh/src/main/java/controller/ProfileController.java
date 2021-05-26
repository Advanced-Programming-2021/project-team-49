package controller;

import model.user.User;
import view.ProfileView;

public class ProfileController extends AbstractController {

    public ProfileController(MasterController masterController, User user) {
        super(masterController, user);
        title = "Profile Menu";
    }

    public void run() {
        new ProfileView(this).run();
    }
}
