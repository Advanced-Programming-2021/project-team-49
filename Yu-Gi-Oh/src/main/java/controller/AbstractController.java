package controller;

import model.user.User;
import view.AbstractView;

public abstract class AbstractController implements Controller {
    protected final MasterController masterController;
    protected final User user;

    protected AbstractController(MasterController masterController, User user) {
        this.masterController = masterController;
        this.user = user;
    }

    public void escape() {
        MainMenuController mainMenuController = new MainMenuController(masterController, user);
        masterController.setNextController(mainMenuController);
    }
}
