package controller;

import model.user.User;
import model.user.Userbase;

public class MainMenuController extends AbstractController {
    public static final String TITLE = "Main Menu";
    private final MasterController masterController;

    public MainMenuController(MasterController masterController, User user, Userbase userbase) {
        this.masterController = masterController;
    }

    public void run() {

    }
}
