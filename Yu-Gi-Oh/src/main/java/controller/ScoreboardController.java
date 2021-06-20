package controller;

import model.database.Userbase;
import model.user.User;
import view.ScoreboardView;

import java.util.*;

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

    public List<List<User>> getScoreboard() {
        List<List<User>> scoreboard = new ArrayList<>();
        int prevScore = -1;

        for (User user : userbase.getUsersSortedByScore()) {
            if (user.getScore() != prevScore) {
                scoreboard.add(new LinkedList<>());
                prevScore = user.getScore();
            }
            scoreboard.get(scoreboard.size() - 1).add(user);
        }
        return scoreboard;
    }
}
