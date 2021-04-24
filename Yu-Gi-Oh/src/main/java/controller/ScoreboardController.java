package controller;

import model.user.User;
import model.user.Userbase;

public class ScoreboardController extends AbstractController {
    public static final String TITLE = "Scoreboard Menu";

    public ScoreboardController(MasterController masterController, User user) {
        super(masterController, user);
    }

    public void run() {

    }

    @Override
    public String toString() {
        return "";
    }
}
