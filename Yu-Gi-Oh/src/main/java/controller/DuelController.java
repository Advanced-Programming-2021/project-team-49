package controller;

import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import model.cardtemplate.*;
import model.game.*;
import model.game.card.Card;
import model.game.card.Monster;
import model.game.card.SpellTrap;
import model.user.User;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;

public class DuelController extends AbstractController {

    private static final String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase",
            "main phase 2", "end phase"};
    private static final int INIT_LIFE_POINTS = 8000;

    private final EffectController effectController;
    private final boolean hasAI;
    private final int rounds;
    private final int currentRound = 1;
    private int phase = 0;
    private int drawCount;
    private Field field;
    private Location selectedCardLocation = null;
    private int selectedCardPosition;
    private boolean isOpponentCardSelected;
    private boolean isMonsterAddedToField = false;

    public DuelController(MasterController masterController, User host, User guest, int rounds, boolean hasAI) {
        super(masterController, host);
        this.rounds = rounds;
        this.hasAI = hasAI;
        title = "Duel Menu";

        Player playerOne = new Player(host, INIT_LIFE_POINTS);
        Player playerTwo = new Player(guest, INIT_LIFE_POINTS);
        field = new Field(playerOne, playerTwo);
        effectController = new EffectController(field, this);
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

    public void surrender() throws EndOfRoundException {
        endRound(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
        throw new EndOfMatchException(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
    }

    public void endRound(Player winner, Player loser) throws EndOfMatchException {
        selectedCardLocation = null;

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
        return field.getAttackerMat().getCard(selectedCardLocation, selectedCardPosition);
    }

    public void changePhase() {
        selectedCardLocation = null;
        phase++;
        if (phase > 5) {
            phase = 0;
            field.switchTurn();
            for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
                ((Monster) card).setPositionChanged(false);
            isMonsterAddedToField = false;
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
        Effect effect = getSelectedCard().getEffect();

        switch (effect) {
            case MONSTER_REBORN:
                effectController.monsterReborn();
                field.getAttackerMat().addCard(getSelectedCard(), Location.GRAVEYARD);
                field.getAttackerMat().removeCard(getSelectedCard(), Location.SPELL_AND_TRAP_ZONE);
                break;

            case TERRAFORMING:
                effectController.terraforming();
                field.getAttackerMat().addCard(getSelectedCard(), Location.GRAVEYARD);
                field.getAttackerMat().removeCard(getSelectedCard(), Location.SPELL_AND_TRAP_ZONE);
                break;

            case POT_OF_GREED:
                effectController.potOfGreed();
                field.getAttackerMat().addCard(getSelectedCard(), Location.GRAVEYARD);
                field.getAttackerMat().removeCard(getSelectedCard(), Location.SPELL_AND_TRAP_ZONE);
                break;

            case RAIGEKI:
                effectController.raigeki();
                field.getAttackerMat().addCard(getSelectedCard(), Location.GRAVEYARD);
                field.getAttackerMat().removeCard(getSelectedCard(), Location.SPELL_AND_TRAP_ZONE);
                break;

            case CHANGE_OF_HEART:

            case HARPIES_FEATHER_DUSTER:
                effectController.harpiesFeatherDuster();
                field.getAttackerMat().addCard(getSelectedCard(), Location.GRAVEYARD);
                field.getAttackerMat().removeCard(getSelectedCard(), Location.SPELL_AND_TRAP_ZONE);

            case ADVANCED_RITUAL_ART:
                if (!isRitualSummonPossible())
                    throw new GameErrorException("there is no way you could ritual summon a monster");
                field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
                getSelectedCard().setFaceUp(true);
                break;
        }
    }

    public void activateSpell() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (!(card instanceof SpellTrap)
                || ((SpellTrap) card).getType() == SpellTrapType.TRAP)
            throw new GameErrorException("activate effect is only for spell cards");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't activate an effect on this turn");
        else if (card.isFaceUp())
            throw new GameErrorException("you have already activated this card");
        else if (getCardCount(Location.SPELL_AND_TRAP_ZONE) >= 5 &&
                ((SpellTrap) card).getEffectType() != EffectType.FIELD)
            throw new GameErrorException("spell card zone is full");
        else
            callEffect();
    }

    private boolean isRitualSummonPossible() {
        int sumOfLevels = 0;
        for (Card card : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
            sumOfLevels += ((Monster) card).getLevel();
        for (Card card : field.getAttackerMat().getCardList(Location.HAND)) {
            if (card instanceof Monster)
                sumOfLevels += ((Monster) card).getLevel();
        }

        List<Card> ritualCards = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.HAND))
            if (card instanceof Monster)
                if (((Monster) card).getCardType() == CardType.RITUAL)
                    ritualCards.add(card);

        if (ritualCards.isEmpty())
            return false;
        for (Card ritualCard : ritualCards)
            if (((Monster) ritualCard).getLevel() > sumOfLevels)
                return false;

        return true;
    }

    private boolean ritualSummon() {
        if (((Monster) getSelectedCard()).getCardType() != CardType.RITUAL)
            return false;

        boolean isRitualSpellActivated = false;
        for (Card card : field.getAttackerMat().getCardList(Location.SPELL_AND_TRAP_ZONE)) {
            if (card.getEffect() == Effect.ADVANCED_RITUAL_ART && card.isFaceUp()) {
                isRitualSpellActivated = true;
                break;
            }
        }
        if (!isRitualSpellActivated)
            throw new GameErrorException("\"Advanced Ritual Art\" must be activated first");

        List<Card> monsterCards = new ArrayList<>(field.getAttackerMat().getCardList(Location.MONSTER_ZONE));
        for (Card card : field.getAttackerMat().getCardList(Location.HAND)) {
            if (card instanceof Monster)
                monsterCards.add(card);
        }

        List<Card> nominatedCardsToTribute = new ArrayList<>();
        int selected;
        int sumOfLevels = 0;
        int ritualCardLevel = ((Monster) getSelectedCard()).getLevel();
        do {
            DuelView.showCardListStringView(monsterCards);
            do {
                selected = DuelView.selectNumber(1, monsterCards.size());
                if (selected == 0)
                    throw new GameErrorException("cancelled");
            } while (selected == -1);
            sumOfLevels += ((Monster) monsterCards.get(selected - 1)).getLevel();
            nominatedCardsToTribute.add(monsterCards.get(selected - 1));
            monsterCards.remove(selected - 1);
        } while (sumOfLevels < ritualCardLevel);

        for (Card card : nominatedCardsToTribute) {
            field.getAttackerMat().addCard(card, Location.GRAVEYARD);
            if (field.getAttackerMat().getCardList(Location.MONSTER_ZONE).contains(card))
                field.getAttackerMat().removeCard(card, Location.MONSTER_ZONE);
            else
                field.getAttackerMat().removeCard(card, Location.HAND);
        }

        do {
            selected = DuelView.selectPosition();
            if (selected == 0)
                throw new GameErrorException("cancelled");
        } while (selected == -1);

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        getSelectedCard().setFaceUp(selected == 1);

        return true;
    }

    private boolean tributeSummonOrSet(boolean summon) {
        int level = ((Monster) getSelectedCard()).getLevel();
        int monsterCardCount = field.getAttackerMat().getCardCount(Location.MONSTER_ZONE);
        if (level < 5)
            return false;
        else if (level == 5 || level == 6) {
            if (monsterCardCount < 2)
                throw new GameErrorException("there are not enough cards for tribute");

            int selected = DuelView.selectNumber(1, monsterCardCount);
            if (selected == -1)
                throw new GameErrorException("there is no monster on this address");

            field.getAttackerMat().removeCard(Location.MONSTER_ZONE, selected);

        } else {
            if (monsterCardCount < 2)
                throw new GameErrorException("there are not enough cards for tribute");

            int selected1 = DuelView.selectNumber(1, monsterCardCount);
            int selected2 = DuelView.selectNumber(1, monsterCardCount);
            if (selected1 == -1 || selected2 == -1 || selected1 == selected2)
                throw new GameErrorException("there is no monster on one of these addresses");

            field.getAttackerMat().removeCard(Location.MONSTER_ZONE, selected1);
            field.getAttackerMat().removeCard(Location.MONSTER_ZONE, selected2);
        }
        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        isMonsterAddedToField = true;
        if (summon)
            getSelectedCard().setFaceUp(true);

        return true;
    }

    public void summon() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND || !(card instanceof Monster))
            throw new GameErrorException("you can't summon this card");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("action not allowed in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
        else if (isMonsterAddedToField)
            throw new GameErrorException("you already summoned/set on this turn");
        else if (ritualSummon())
            return;
        else if (tributeSummonOrSet(true))
            return;

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        card.setFaceUp(true);
        isMonsterAddedToField = true;
    }

    public void setPosition(String position) {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("you can't change this card position");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (!position.equalsIgnoreCase("attack") && !position.equalsIgnoreCase("defense"))
            throw new GameErrorException("invalid position");

        Monster monster = (Monster) card;
        if (position.equalsIgnoreCase("attack") && monster.isAttacker()
                || position.equalsIgnoreCase("defense") && !monster.isAttacker())
            throw new GameErrorException("this card is already in the wanted position");
        else if (monster.isPositionChanged())
            throw new GameErrorException("you already changed this card position in this turn");

        monster.setAttacker(position.equalsIgnoreCase("attack"));
        monster.setPositionChanged(true);
    }

    public void setMonster() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this card");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
        else if (isMonsterAddedToField)
            throw new GameErrorException("you already summoned/set on this turn");
        else if (tributeSummonOrSet(false))
            return;

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        isMonsterAddedToField = true;
    }

    public void setSpellOrTrap() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this card");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (card instanceof SpellTrap && ((SpellTrap) card).getEffectType() == EffectType.FIELD) {
            field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.FIELD_ZONE);
            return;
        } else if (field.getAttackerMat().getCardCount(Location.SPELL_AND_TRAP_ZONE) == 5)
            throw new GameErrorException("spell card zone is full");

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
    }

    public void showGraveyard(boolean opponent) {
        List<Card> graveyard;
        if (opponent)
            graveyard = field.getDefenderMat().getCardList(Location.GRAVEYARD);
        else
            graveyard = field.getAttackerMat().getCardList(Location.GRAVEYARD);

        if (graveyard.isEmpty())
            throw new GameErrorException("graveyard is empty");
        else
            DuelView.showCardListStringView(graveyard);
    }

    public void showSelectedCard() {
        // TODO check phase 1 doc, page 11 (card show <card name>)
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (isOpponentCardSelected && !card.isFaceUp())
            throw new GameErrorException("card is not visible");

        DuelView.showCardInfoStringView(card);
    }

    public void attack(int targetPosition) throws EndOfRoundException {
        if (targetPosition < 1 || targetPosition > 5)
            throw new GameErrorException("card position number must be between 1 and 5");
        else if (selectedCardLocation == null)
            throw new GameErrorException("no card is selected yet");
        else if (isOpponentCardSelected
                || selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("you can't attack with this card");
        else if (phase != 3)
            throw new GameErrorException("you can't do this action in this phase");
        else {
            Monster attacker = (Monster) getSelectedCard();
            Monster target = (Monster) field.getDefenderMat().getCard(Location.MONSTER_ZONE, targetPosition);

            if (attacker.isUsedInAttack())
                throw new GameErrorException("this card already attacked");
            else if (target == null)
                throw new GameErrorException("there is no card to attack here");

            attackMonster(attacker, target, selectedCardPosition, targetPosition);
        }
    }

    public void attackMonster(Monster attacker, Monster target, int attackerPosition, int targetPosition)
            throws EndOfRoundException {
        int damage;

        if (target.isAttacker()) {
            damage = attacker.getAttack() - target.getAttack();

            if (damage > 0) {
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, targetPosition, Location.GRAVEYARD);
                field.getDefenderMat().getPlayer().removeLifePoints(damage);
            } else if (damage == 0) {
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, targetPosition, Location.GRAVEYARD);
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attackerPosition, Location.GRAVEYARD);
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attackerPosition, Location.GRAVEYARD);
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
            }
        } else {
            damage = attacker.getAttack() - target.getDefense();

            if (damage > 0)
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, targetPosition, Location.GRAVEYARD);
            else if (damage < 0)
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
        }

        if (!target.isAttacker() && !target.isFaceUp()) {
            DuelView.showAttackOutcome(target.getName(), damage);
            target.setFaceUp(true);
        } else
            DuelView.showAttackOutcome(!target.isAttacker(), damage);

        checkEndOfRoundWithLifePoints();
    }

    public void checkEndOfRoundWithLifePoints() throws EndOfRoundException {
        if (field.getDefenderMat().getPlayer().getLifePoints() <= 0) {
            Player winner = field.getAttackerMat().getPlayer();
            Player loser = field.getDefenderMat().getPlayer();

            endRound(winner, loser);
            throw new EndOfRoundException(winner, loser);
        } else if (field.getAttackerMat().getPlayer().getLifePoints() <= 0) {
            Player winner = field.getDefenderMat().getPlayer();
            Player loser = field.getAttackerMat().getPlayer();

            endRound(winner, loser);
            throw new EndOfRoundException(winner, loser);
        }
    }
}