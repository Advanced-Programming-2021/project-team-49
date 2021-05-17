package controller;

import model.user.User;

public abstract class AbstractController implements Controller {
    protected static String title;
    protected final MasterController masterController;
    protected final User user;

    protected AbstractController(MasterController masterController, User user) {
        this.masterController = masterController;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void escape() {
        MainMenuController mainMenuController = new MainMenuController(masterController, user,
                masterController.getDatabase());
        masterController.setNextController(mainMenuController);
    }
}
