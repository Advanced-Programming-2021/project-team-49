package model.game;

import exception.EndOfRoundException;
import model.user.User;

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

    public Card drawCard() throws EndOfRoundException {
        if (attackerMat.getCardCount(Location.DECK) == 0)
            throw new EndOfRoundException(defenderMat.getPlayer());
        else {
            attackerMat.moveCard(Location.DECK, Location.HAND);
            return attackerMat.getCard(Location.HAND);
        }
    }
}