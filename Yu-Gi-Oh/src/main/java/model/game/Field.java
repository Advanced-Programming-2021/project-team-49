package model.game;

import exception.EndOfRoundException;

public class Field {

    private GameMat attackerMat;
    private GameMat defenderMat;

    public Field(GameMat attackerMat, GameMat defenderMat) {
        this.attackerMat = attackerMat;
        this.defenderMat = defenderMat;
    }

    public Field(Player playerOne, Player playerTwo) {
        attackerMat = new GameMat(playerOne, playerOne.getUser().getActiveDeck().getGameDeck());
        defenderMat = new GameMat(playerTwo, playerTwo.getUser().getActiveDeck().getGameDeck());
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
            throw new EndOfRoundException(defenderMat.getPlayer(), attackerMat.getPlayer());
        else {
            attackerMat.moveCard(Location.DECK, Location.HAND);
            return attackerMat.getCard(Location.HAND);
        }
    }
}