package controller;

import exception.YugiohException;
import model.database.Userbase;
import model.user.User;
import view.LoginView;

public class LoginController extends AbstractController {
    private static final String TITLE = "Login Menu";
    public final Userbase userbase;

    public LoginController(MasterController masterController, Userbase userbase) {
        super(masterController, null);
        this.userbase = userbase;
    }

    public void run() {
        new LoginView(this);
    }

    public void createUser(String username, String nickname, String password) {
        if (userbase.getUserByUsername(username) != null)
            throw new YugiohException("user with username " + username + " already exists");
        else if (userbase.getUserByNickname(username) != null)
            throw new YugiohException("user with nickname " + nickname + " already exists");

        userbase.addUser(username, nickname, password);
    }

    public void login(String username, String password) {
        User user = userbase.getUserByUsername(username);
        if (user == null || !user.isPasswordCorrect(password))
            throw new YugiohException("Username and password didn't match!");

        MainMenuController mainMenuController = new MainMenuController(
                masterController, user, masterController.getDatabase());
        masterController.setNextController(mainMenuController);
    }

    @Override
    public void escape() {
        masterController.setNextController(null);
    }
}
