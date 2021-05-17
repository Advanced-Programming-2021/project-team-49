package controller;

import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import model.game.*;
import model.user.User;
import view.DuelView;

public class DuelController extends AbstractController {

    private static final String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase",
            "main phase 2", "end phase"};

    private final User guest;
    private final boolean hasAI;
    private final int rounds;
    private final int currentRound = 1;
    private int phase = -1;
    private int playerOneWinCount = 0;
    private int playerTwoWinCount = 0;
    private int drawCount;
    private Field field;

    private Location selectedCardLocation = null;
    private int selectedCardPosition;

    public DuelController(MasterController masterController, User host, User guest, int rounds, boolean hasAI) {
        super(masterController, host);
        this.guest = guest;
        this.rounds = rounds;
        this.hasAI = hasAI;
        title = "Duel Menu";
    }

    public String getPhaseName() {
        return phaseNames[phase];
    }

    public int getPhaseNumber() {
        return phase;
    }

    public User getCurrentPlayer() {
        return field.getAttackerMat().getPlayer();
    }

    public void run() {
        new DuelView(this);
    }

    public void endRound(EndOfRoundException exception) throws EndOfMatchException {
        addWinCount(exception.getWinner());
        setExceptionScores(exception);

        if (isMatchEnded())
            throw new EndOfMatchException(exception);
    }

    private void addWinCount(User winner) {
        if (winner == guest)
            playerTwoWinCount++;
        else
            playerOneWinCount++;
    }

    private void setExceptionScores(EndOfRoundException exception) {
        if (exception.getWinner() == guest)
            exception.setScores(playerTwoWinCount, playerOneWinCount);
        else
            exception.setScores(playerOneWinCount, playerTwoWinCount);
    }

    public boolean isMatchEnded() {
        return playerOneWinCount > rounds / 2
                || playerTwoWinCount > rounds / 2;
    }

    public void selectCard(Location location, int position, boolean opponent) {
        GameMat gameMat;
        if (opponent) {
            if (location == Location.HAND)
                throw new GameErrorException("invalid selection");
            gameMat = field.getDefenderMat();
        } else
            gameMat = field.getAttackerMat();

        try {
            if (gameMat.getCard(location, position) == null)
                throw new GameErrorException("no card found in the given position");
        } catch (IndexOutOfBoundsException exception) {
            throw new GameErrorException("invalid selection", exception);
        }

        selectedCardLocation = location;
        selectedCardPosition = position;
    }

    public void deselectCard() {
        if (selectedCardLocation == null)
            throw new GameErrorException("no card is selected yet");
        selectedCardLocation = null;
    }

    public void changePhase() {
        phase++;
        if (phase > 5) {
            phase = 0;
            field.switchTurn();
        }
    }

    public void drawCard() throws EndOfRoundException {
        try {
            field.drawCard();
        } catch (EndOfRoundException exception) {
            endRound(exception);
            throw exception;
        }
    }
}