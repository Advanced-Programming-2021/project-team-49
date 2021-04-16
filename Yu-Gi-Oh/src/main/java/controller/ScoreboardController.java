package controller;

import model.user.Userbase;

public class ScoreboardController extends AbstractController {
    public static final String TITLE = "Scoreboard Menu";
    private final MasterController masterController;

    public ScoreboardController(MasterController masterController, Userbase userbase) {
        this.masterController = masterController;
    }

    public void run() {

    }

    @Override
    public String toString() {
        return "";
    }
}
