package controller;

import model.game.Field;
import model.game.Phase;
import model.user.User;

public class DuelController extends AbstractController {

    public static final String TITLE = "Duel Menu";

    private final User guest;
    private final int rounds;
    private final boolean hasAI;
    private int player1WinCount;
    private int player2WinCount;
    private int drawCount;
    private Field field;
    private Phase currentPhase;

    public DuelController(MasterController masterController, User host, User guest, int rounds, boolean hasAI) {
        super(masterController, host);
        this.guest = guest;
        this.rounds = rounds;
        this.hasAI = hasAI;
    }

    public void run() {

    }
}