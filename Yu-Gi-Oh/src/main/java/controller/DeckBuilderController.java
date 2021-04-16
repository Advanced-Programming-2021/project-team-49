package controller;

import model.user.User;

public class DeckBuilderController extends AbstractController {
    public static final String TITLE = "Deck Menu";
    private final MasterController masterController;

    public DeckBuilderController(MasterController masterController, User user) {
        this.masterController = masterController;
    }

    public void run() {

    }
}
