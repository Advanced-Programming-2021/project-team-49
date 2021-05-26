package controller;

import model.database.Userbase;
import model.user.User;
import view.ScoreboardView;

public class ScoreboardController extends AbstractController {

    private final Userbase userbase;

    public ScoreboardController(MasterController masterController, User user, Userbase userbase) {
        super(masterController, user);
        this.userbase = userbase;
        title = "Scoreboard Menu";
    }

    public void run() {
        new ScoreboardView(this).run();
    }

    @Override
    public String toString() {
        return "";
    }
}
