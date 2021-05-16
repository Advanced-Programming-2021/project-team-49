package controller;

import exception.GameErrorException;
import model.game.*;
import model.user.User;
import view.DuelView;

public class DuelController extends AbstractController {

    private static final String TITLE = "Duel Menu";
    private static final String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase",
            "main phase 2", "end phase"};

    private final User guest;
    private final boolean hasAI;
    private final int rounds;
    private int currentRound = 1;
    private int phase = 0;
    private int player1WinCount = 0;
    private int player2WinCount = 0;
    private int drawCount;
    private Field field;

    private Location selectedCardLocation = null;
    private int selectedCardPosition;

    public DuelController(MasterController masterController, User host, User guest, int rounds, boolean hasAI) {
        super(masterController, host);
        this.guest = guest;
        this.rounds = rounds;
        this.hasAI = hasAI;
    }

    public static String getPhaseName(int phase) {
        return phaseNames[phase];
    }

    public int getPhaseNumber() {
        return phase;
    }

    public void run() {
        new DuelView(this);
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
            throw new GameErrorException("invalid selection");
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
}