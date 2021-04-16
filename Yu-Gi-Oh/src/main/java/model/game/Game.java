package model.game;

import model.user.User;

public class Game {

    private final User player1;
    private final User player2;
    private Field field;
    private int player1WinCount;
    private int player2WinCount;
    private int drawCount;
    private Phase currentPhase;

    public Game(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}