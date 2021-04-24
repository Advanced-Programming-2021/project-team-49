package controller;

import model.database.Userbase;

public class LoginController extends AbstractController {
    public static final String TITLE = "Login Menu";
    public final Userbase userbase;

    public LoginController(MasterController masterController, Userbase userbase) {
        super(masterController, null);
        this.userbase = userbase;
    }

    public void run() {

    }

    public void registerUser(String username, String nickname, String password) {

    }

    public void login(String username, String password) {

    }

    public void removeUser(String username, String password) {

    }

    @Override
    public void escape() {
        masterController.setNextController(null);
    }
}
