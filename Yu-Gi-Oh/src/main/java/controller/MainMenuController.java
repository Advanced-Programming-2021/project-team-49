package controller;

import exception.GameErrorException;
import model.user.User;

public class MainMenuController extends Controller {

    public DuelController startPlayerDuel(String secondPlayerUsername, int rounds) {
        if (secondPlayerUsername.length() == 0)
            throw new GameErrorException("Enter the second player's username");
        User secondPlayer = DATABASE.getUserbase().getUserByUsername(secondPlayerUsername);

        if (secondPlayer == null)
            throw new GameErrorException("There is no player with this username");
        else if (secondPlayer.equals(user))
            throw new GameErrorException("Enter another player's username");
        else if (secondPlayer.getActiveDeck() == null)
            throw new GameErrorException(secondPlayerUsername + " has no active deck");
        else if (!secondPlayer.getActiveDeck().isDeckValid())
            throw new GameErrorException(secondPlayerUsername + "'s deck is invalid");
        else if (rounds != 1 && rounds != 3)
            System.out.println("Number of rounds is not supported");

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
