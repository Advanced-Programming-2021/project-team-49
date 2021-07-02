package controller;

import exception.GameErrorException;
import model.database.Database;
import model.user.User;
import view.MainMenuView;

public class MainMenuController extends Controller {

    public void startPlayerDuel(String secondPlayerUsername, int rounds) {
        User secondPlayer = DATABASE.getUserbase().getUserByUsername(secondPlayerUsername);

        if (secondPlayer == null)
            throw new GameErrorException("there is no player with this username");
        else if (secondPlayer.getActiveDeck() == null)
            throw new GameErrorException(secondPlayerUsername + " has no active deck");
        else if (!secondPlayer.getActiveDeck().isDeckValid())
            throw new GameErrorException(secondPlayerUsername + "'s deck is invalid");
        else if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        DuelController duelController = new DuelController(user, secondPlayer, rounds, false);
    }

    public void startAIDuel(int rounds) {
        if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        DuelController duelController = new DuelController(user, null, rounds, true);
    }
}
