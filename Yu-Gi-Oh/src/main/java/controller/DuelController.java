package controller;

import model.game.Field;
import model.game.Phase;
import model.user.User;

public class DuelController extends AbstractController {

    public static final String TITLE = "Duel Menu";

    private final User guest;
    private Field field;
    private int player1WinCount;
    private int player2WinCount;
    private int drawCount;
    private Phase currentPhase;

    public DuelController(MasterController masterController, User host, User guest) {
        super(masterController, host);
        this.guest = guest;
    }

    public void run() {

    }
}
