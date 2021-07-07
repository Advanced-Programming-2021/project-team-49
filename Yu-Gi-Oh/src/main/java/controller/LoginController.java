package controller;

import exception.GameErrorException;
import model.database.Userbase;
import model.user.User;

public class LoginController extends Controller {

    public final Userbase userbase = DATABASE.getUserbase();

    public void login(String username, String password) {
        checkEmptyFields(username, password);

        User user = userbase.getUserByUsername(username);
        if (user == null || !user.isPasswordCorrect(password))
            throw new GameErrorException("Username and password didn't match!");

        Controller.user = user;
    }

    private void checkEmptyFields(String username, String password) {
        if (username.length() == 0)
            throw new GameErrorException("Enter username");
        if (password.length() == 0)
            throw new GameErrorException("Enter password");
    }
}
