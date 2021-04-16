package model.game;

import model.user.User;

public class Game {

    User player1;
    User player2;
    Field field;

    public Game(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
