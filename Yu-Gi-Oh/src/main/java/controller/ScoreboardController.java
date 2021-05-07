package controller;

import model.database.Userbase;
import model.user.User;

public class ScoreboardController extends AbstractController {
    public static final String TITLE = "Scoreboard Menu";
    private final Userbase userbase;

    public ScoreboardController(MasterController masterController, User user, Userbase userbase) {
        super(masterController, user);
        this.userbase = userbase;
    }

    public void run() {

    }

    @Override
    public String toString() {
        return "";
    }
}
