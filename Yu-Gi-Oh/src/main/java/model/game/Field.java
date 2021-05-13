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

    public Field(User playerOne, User playerTwo, List<Card> playerOneDeck, List<Card> playerTwoDeck,
            int playerOneLifePoints, int playerTwoLifePoints) {
        attackerMat = new GameMat(playerOne, playerOneDeck, playerOneLifePoints);
        defenderMat = new GameMat(playerTwo, playerTwoDeck, playerTwoLifePoints);
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

    @Override
    public String toString() {
        return defenderMat.getStringView(true)
                + "\n\n--------------------------\n\n"
                + attackerMat.getStringView(false);
    }
}