package view;

import controller.ScoreboardController;
import model.user.User;

import java.util.List;
import java.util.SortedMap;

public class ScoreboardView extends AbstractView {

    private final ScoreboardController controller;

    public ScoreboardView(ScoreboardController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        if (input.equals("scoreboard show"))
            printScoreboard(controller.getScoreboard());
        else
            return runDefaultCommands(input, controller);

        return true;
    }

    public void printScoreboard(List<List<User>> scoreboard) {
        StringBuilder outputBuilder = new StringBuilder();

        for (int i = 0; i < scoreboard.size(); i++)
            for (User user : scoreboard.get(i))
                outputBuilder.append(i + 1).append("- ")
                        .append(user.getNickname()).append(": ")
                        .append(user.getScore()).append("\n");

        System.out.print(outputBuilder);
    }
}
