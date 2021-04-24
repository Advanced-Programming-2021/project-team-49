package controller;

import model.user.User;
import model.user.Userbase;

public class MasterController implements Controller {
    private Controller nextController;

    public void run() {
        nextController = new LoginController(this);
        while (nextController != null)
            nextController.run();
    }

    public void setNextController(Controller nextController) {
        this.nextController = nextController;
    }
}
