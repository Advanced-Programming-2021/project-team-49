package controller;

import model.user.User;

public class DuelController extends AbstractController {
    public static final String TITLE = "Duel Menu";

    public DuelController(MasterController masterController, User host, User guest) {
        super(masterController, host);
    }

    public void run() {

    }
}
