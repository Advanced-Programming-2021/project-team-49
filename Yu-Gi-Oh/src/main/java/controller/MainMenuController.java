package controller;

import model.database.Database;
import model.user.User;

public class MainMenuController extends AbstractController {
    public static final String TITLE = "Main Menu";
    private Database database;

    public MainMenuController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
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
        LoginController loginController = new LoginController(masterController, database.getUserbase());
        masterController.setNextController(loginController);
    }
}
