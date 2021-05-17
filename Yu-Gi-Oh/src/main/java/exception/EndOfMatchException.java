package exception;

public class EndOfMatchException extends EndOfRoundException {

    public EndOfMatchException(EndOfRoundException cause) {
        super(cause, cause.getWinner());
        super.setScores(cause.getWinnerScore(), cause.getLoserScore());
    }
}
