package controller;

public class LoginController extends AbstractController {
    public static final String TITLE = "Login Menu";

    public LoginController(MasterController masterController) {
        super(masterController, null);
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
