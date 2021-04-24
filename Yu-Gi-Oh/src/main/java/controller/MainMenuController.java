package controller;

import model.user.User;
import model.user.Userbase;

public class MainMenuController extends AbstractController {
    public static final String TITLE = "Main Menu";

    public MainMenuController(MasterController masterController, User user) {
        super(masterController, user);
    }

    public void run() {

    }

    public void openShop() {

    }

    public void openProfile() {

    }

    public void startDuel() {

    }

    @Override
    public void escape() {
        LoginController loginController = new LoginController(masterController);
        masterController.setNextController(loginController);
    }
}
