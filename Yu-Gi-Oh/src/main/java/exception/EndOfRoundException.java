package exception;

import model.game.Player;

public class EndOfRoundException extends Exception {

    private final Player winner;
    private final Player loser;

    public EndOfRoundException(Player winner, Player loser) {
        super();
        this.winner = winner;
        this.loser = loser;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }
}
