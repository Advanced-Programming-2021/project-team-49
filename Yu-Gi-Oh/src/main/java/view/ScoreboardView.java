package view;

import controller.ScoreboardController;

public class ScoreboardView extends AbstractView {

    private final ScoreboardController controller;

    public ScoreboardView(ScoreboardController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        return true;
    }
}
