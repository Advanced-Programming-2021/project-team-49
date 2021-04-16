package controller;

import model.user.User;

public class DuelController extends AbstractController {
    public static final String TITLE = "Duel Menu";
    private final MasterController masterController;

    public DuelController(MasterController masterController, User playerOne, User playerTwo) {
        this.masterController = masterController;
    }

    public void run() {

    }
}
