package exception;

import model.user.User;

public class EndOfRoundException extends Exception {

    private final User winner;
    private int winnerScore;
    private int loserScore;

    public EndOfRoundException(User winner) {
        super();
        this.winner = winner;
    }

    public EndOfRoundException(Throwable cause, User winner) {
        super(cause);
        this.winner = winner;
    }

    public User getWinner() {
        return winner;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setScores(int winnerScore, int loserScore) {
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }
}
