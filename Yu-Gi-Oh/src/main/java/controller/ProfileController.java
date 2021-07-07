package controller;

import exception.GameErrorException;
import model.database.Userbase;
import model.user.User;
import view.ProfileView;

public class ProfileController extends Controller {

    private final Userbase userbase = DATABASE.getUserbase();

    public void changePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        checkEmptyFields(oldPassword, newPassword, confirmNewPassword);

        if (user.isPasswordCorrect(oldPassword))
            if (!newPassword.equals(confirmNewPassword))
                throw new GameErrorException("New password confirmation didn't match");
            else if (oldPassword.equals(newPassword))
                throw new GameErrorException("Please enter a new password");
            else
                user.setPassword(newPassword);
        else
            throw new GameErrorException("Current password is invalid");
    }

    private void checkEmptyFields(String oldPassword, String newPassword, String confirmNewPassword) {
        if (oldPassword.length() == 0)
            throw new GameErrorException("Enter current password");
        if (newPassword.length() == 0)
            throw new GameErrorException("Enter new password");
        if (confirmNewPassword.length() == 0)
            throw new GameErrorException("Confirm new password");
    }

    public void changeNickname(String newNickname) {
        if (newNickname.length() == 0)
            throw new GameErrorException("Enter a nickname");
        if (userbase.getUserByNickname(newNickname) != null)
            throw new GameErrorException("User with nickname " + newNickname + " already exists");
        user.setNickname(newNickname);
    }

    public User getUser() {
        return user;
    }
}
