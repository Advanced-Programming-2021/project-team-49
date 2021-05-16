package model.game;

import model.user.User;

import java.util.List;

public class Field {

    private GameMat attackerMat;
    private GameMat defenderMat;

    public Field(GameMat attackerMat, GameMat defenderMat) {
        this.attackerMat = attackerMat;
        this.defenderMat = defenderMat;
    }

    public Field(User playerOne, User playerTwo, int playerOneLifePoints, int playerTwoLifePoints) {
        attackerMat = new GameMat(playerOne, playerOne.getActiveDeck().getGameDeck(), playerOneLifePoints);
        defenderMat = new GameMat(playerTwo, playerTwo.getActiveDeck().getGameDeck(), playerTwoLifePoints);
    }

    public GameMat getAttackerMat() {
        return attackerMat;
    }

    public GameMat getDefenderMat() {
        return defenderMat;
    }

    public void switchTurn() {
        GameMat tempMat = attackerMat;
        attackerMat = defenderMat;
        defenderMat = tempMat;
    }
}