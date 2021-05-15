package controller;

import model.user.User;

public abstract class AbstractController implements Controller {
    private static final String TITLE = "Menu";
    protected final MasterController masterController;
    protected final User user;

    public String getTitle() {
        return TITLE;
    }

    protected AbstractController(MasterController masterController, User user) {
        this.masterController = masterController;
        this.user = user;
    }

    public void escape() {
        MainMenuController mainMenuController = new MainMenuController(masterController, user,
                masterController.getDatabase());
        masterController.setNextController(mainMenuController);
    }
}
