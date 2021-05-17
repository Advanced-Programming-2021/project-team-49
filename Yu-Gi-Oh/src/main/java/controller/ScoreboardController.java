package controller;

import model.database.Userbase;
import model.user.User;

public class ScoreboardController extends AbstractController {

    private final Userbase userbase;

    public ScoreboardController(MasterController masterController, User user, Userbase userbase) {
        super(masterController, user);
        this.userbase = userbase;
        title = "Main Menu";
    }

    public void run() {

    }

    @Override
    public String toString() {
        return "";
    }
}
