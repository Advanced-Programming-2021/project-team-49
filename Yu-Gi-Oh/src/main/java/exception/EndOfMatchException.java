package exception;

import model.game.Player;

public class EndOfMatchException extends EndOfRoundException {

    public EndOfMatchException(Player winner, Player loser) {
        super(winner, loser);
    }
}
