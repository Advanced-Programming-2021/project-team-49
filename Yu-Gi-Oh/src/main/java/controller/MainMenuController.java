package controller;

import exception.GameErrorException;
import model.user.User;

public class MainMenuController extends Controller {

    public DuelController startPlayerDuel(String secondPlayerUsername, int rounds) {
        User secondPlayer = DATABASE.getUserbase().getUserByUsername(secondPlayerUsername);

        if (secondPlayer == null)
            throw new GameErrorException("there is no player with this username");
        else if (secondPlayer.getActiveDeck() == null)
            throw new GameErrorException(secondPlayerUsername + " has no active deck");
        else if (!secondPlayer.getActiveDeck().isDeckValid())
            throw new GameErrorException(secondPlayerUsername + "'s deck is invalid");
        else if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        return new DuelController(user, secondPlayer, rounds, false);
    }

    public DuelController startAIDuel(int rounds) {
        if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        return new DuelController(user, null, rounds, true);
    }

    public void logout() {
        user = null;
    }
}
