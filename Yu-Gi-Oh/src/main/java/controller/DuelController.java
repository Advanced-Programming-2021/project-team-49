package controller;

import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import model.card.Effect;
import model.card.EffectType;
import model.card.Spell;
import model.game.*;
import model.user.User;
import view.DuelView;

public class DuelController extends AbstractController {

    private static final String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase",
            "main phase 2", "end phase"};
    private static final int INIT_LIFE_POINTS = 8000;

    private final boolean hasAI;
    private final int rounds;
    private final int currentRound = 1;
    private int phase = -1;
    private int drawCount;
    private Field field;
    private final EffectController effectController;
    private Location selectedCardLocation = null;
    private int selectedCardPosition;
    private boolean isOpponentCardSelected;
    private boolean isMonsterAddedToFiled = false;

    public DuelController(MasterController masterController, User host, User guest, int rounds, boolean hasAI) {
        super(masterController, host);
        this.rounds = rounds;
        this.hasAI = hasAI;
        title = "Duel Menu";

        Player playerOne = new Player(host, INIT_LIFE_POINTS);
        Player playerTwo = new Player(guest, INIT_LIFE_POINTS);
        field = new Field(playerOne, playerTwo);
        effectController = new EffectController(field);
    }

    public String getPhaseName() {
        return phaseNames[phase];
    }

    public int getPhaseNumber() {
        return phase;
    }

    public Player getCurrentPlayer() {
        return field.getAttackerMat().getPlayer();
    }

    public int getCardCount(Location location) {
        return field.getAttackerMat().getCardCount(location);
    }

    public void run() {
        new DuelView(this).run();
    }

    public void endRound(Player winner, Player loser) throws EndOfMatchException {
        winner.incrementWinCount();
        winner.setCurrentMaxLifePoints();

        winner.setLifePoints(INIT_LIFE_POINTS);
        loser.setLifePoints(INIT_LIFE_POINTS);
        field = new Field(winner, loser);

        if (isMatchEnded()) {
            endMatch(winner, loser);
            throw new EndOfMatchException(winner, loser);
        }
    }

    public void endMatch(Player winner, Player loser) {
        winner.getUser().addCoins(rounds * (1000 + winner.getMaxLifePoints()));
        loser.getUser().addCoins(rounds * 100);

        winner.getUser().addScore(rounds * 1000);
    }

    public boolean isMatchEnded() {
        return field.getAttackerMat().getPlayer().getWinCount() > rounds / 2
                || field.getDefenderMat().getPlayer().getWinCount() > rounds / 2;
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
            throw new GameErrorException("invalid selection", exception);
        }

        selectedCardLocation = location;
        selectedCardPosition = position;
        isOpponentCardSelected = opponent;
    }

    public void deselectCard() {
        if (selectedCardLocation == null)
            throw new GameErrorException("no card is selected yet");
        selectedCardLocation = null;
    }

    public Card getSelectedCard() {
        if (isOpponentCardSelected)
            throw new GameErrorException("invalid selection");

        return field.getAttackerMat().getCard(selectedCardLocation, selectedCardPosition);
    }

    public void changePhase() {
        phase++;
        if (phase > 5) {
            phase = 0;
            field.switchTurn();
            for (Card card : field.getAttackerMat().getLocationList(Location.MONSTER_ZONE))
                card.setPositionChanged(false);
            isMonsterAddedToFiled = false;
        }
    }

    public Card drawCard() throws EndOfRoundException {
        try {
            return field.drawCard();
        } catch (EndOfRoundException exception) {
            endRound(exception.getWinner(), exception.getLoser());
            throw exception;
        }
    }

    private void callEffect() {
        Effect effect = getSelectedCard().getCardTemplate().getEffect();
        try {
            switch (effect) {
                case MONSTER_REBORN:
                    effectController.monsterReborn();
                    break;

                case YAMI:
                    break;
            }
        } catch (Exception e) {
            throw new GameErrorException(e.getMessage());
        }
    }

    public void activateSpell() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (!(card.getCardTemplate() instanceof Spell))
            throw new GameErrorException("activate effect is only for spell cards");
        else if (phase != 2 || phase != 4)
            throw new GameErrorException("you can't activate an effect on this turn");
        else if (card.isFaceUp())
            throw new GameErrorException("you have already activated this card");
        else if (getCardCount(Location.SPELL_AND_TRAP_ZONE) >= 5 &&
                ((Spell) card.getCardTemplate()).getEffectType() != EffectType.FIELD)
            throw new GameErrorException("spell card zone is full");
        else
            try {
                callEffect();
            } catch (Exception e) {
                throw new GameErrorException("preparation of this spell are not done yet");
            }
    }

    public void setPosition(String position) {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("you can't change this card position");
        else if (phase != 2 || phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (!position.equalsIgnoreCase("attack") || !position.equalsIgnoreCase("defense"))
            throw new GameErrorException("invalid position");
        else if (position.equalsIgnoreCase("attack") && card.isAttacker()
                || position.equalsIgnoreCase("defense") && !card.isAttacker())
            throw new GameErrorException("this card is already in the wanted position");
        else if (card.isPositionChanged())
            throw new GameErrorException("you already changed this card position in this turn");

        card.setAttacker(position.equalsIgnoreCase("attack"));
        card.setPositionChanged(true);
    }

    public void setMonster() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this card");
        else if (phase != 2 || phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
        else if (isMonsterAddedToFiled)
            throw new GameErrorException("you already summoned/set on this turn");
        // TODO use isMonsterAddedToField in summon method

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        isMonsterAddedToFiled = true;
    }

    public void setSpellOrTrap() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this card");
        else if (phase != 2 || phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (field.getAttackerMat().getCardCount(Location.SPELL_AND_TRAP_ZONE) == 5)
            throw new GameErrorException("spell card zone is full");
        // TODO Field spells have their own place and the condition above is not for them

        // TODO check if cards are already face down or call setFaceUp(false)
        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
    }
}