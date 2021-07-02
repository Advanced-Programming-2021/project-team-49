package controller;

import exception.GameErrorException;
import model.database.Userbase;
import model.user.User;
import view.ProfileView;

public class ProfileController extends Controller {

    private final Userbase userbase = DATABASE.getUserbase();

    public void run() {
        new ProfileView(this).run();
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (user.isPasswordCorrect(oldPassword))
            if (oldPassword.equals(newPassword))
                throw new GameErrorException("please enter a new password");
            else
                user.setPassword(newPassword);
        else
            throw new GameErrorException("current password is invalid");
    }

    public void changeNickname(String newNickname) {
        if (userbase.getUserByNickname(newNickname) != null)
            throw new GameErrorException("user with nickname " + newNickname + " already exists");
        user.setNickname(newNickname);
    }
}
