package controller;

import model.user.User;
import model.user.Userbase;

public class MasterController implements Controller {
    private Controller nextController;
    private Userbase userbase;
    private User user;

    public void run() {

    }

    public void setNextController(String nextControllerTitle) {

    }

    public void setUserbase(Userbase userbase) {
        this.userbase = userbase;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
