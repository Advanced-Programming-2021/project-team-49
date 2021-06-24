package model.game;

import controller.EffectHandler;
import controller.effects.Event;
import exception.EndOfRoundException;
import model.game.card.Card;
import model.game.card.Monster;

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
        drawStartingHands();
    }

    public GameMat getAttackerMat() {
        return attackerMat;
    }

    public GameMat getDefenderMat() {
        return defenderMat;
    }

    public void switchTurn() {
        for (Card card : attackerMat.getCardList(Location.MONSTER_ZONE)) {
            Monster monster = (Monster) card;
            monster.setPositionChanged(false);
            monster.setUsedInAttack(false);
        }
        attackerMat.notifyEffects(Event.END_TURN);
        switchMats();
    }

    public void switchMats() {
        GameMat tempMat = attackerMat;
        attackerMat = defenderMat;
        defenderMat = tempMat;
    }

    public Card drawCard() throws EndOfRoundException {
        if (attackerMat.getCardCount(Location.HAND) >= 6)
            return null;
        if (attackerMat.getCardCount(Location.DECK) <= 0)
            throw new EndOfRoundException(defenderMat.getPlayer(), attackerMat.getPlayer());
        else {
            attackerMat.moveCard(Location.DECK, Location.HAND);
            return attackerMat.getCard(Location.HAND);
        }
    }

    private void drawStartingHands() {
        for (int i = 0; i < 5; i++) {
            attackerMat.moveCard(Location.DECK, Location.HAND);
            defenderMat.moveCard(Location.DECK, Location.HAND);
        }
    }
}