package controller;

import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.GameErrorException;
import model.cardtemplate.*;
import model.game.*;
import model.game.card.Castable;
import model.game.card.Monster;
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

    public void surrender() throws EndOfRoundException {
        endRound(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
        throw new EndOfMatchException(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
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

    public CardTemplate getSelectedCard() {
        return field.getAttackerMat().getCard(selectedCardLocation, selectedCardPosition);
    }

    public void changePhase() {
        phase++;
        if (phase > 5) {
            phase = 0;
            field.switchTurn();
            for (CardTemplate cardTemplate : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
                ((Monster) cardTemplate).setPositionChanged(false);
            isMonsterAddedToField = false;
        }
    }

    public CardTemplate drawCard() throws EndOfRoundException {
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
                break;

            case ADVANCED_RITUAL_ART:
                if (isRitualSummonPossible())

                break;
        }
    }

    public void activateSpell() {
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (!(cardTemplate instanceof SpellTrapCard)
                || ((SpellTrapCard) cardTemplate).getType() == Type.TRAP)
            throw new GameErrorException("activate effect is only for spell cards");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't activate an effect on this turn");
        else if (((Castable) cardTemplate).isFaceUp())
            throw new GameErrorException("you have already activated this cardTemplate");
        else if (getCardCount(Location.SPELL_AND_TRAP_ZONE) >= 5 &&
                ((SpellTrapCard) cardTemplate).getEffectType() != EffectType.FIELD)
            throw new GameErrorException("spell cardTemplate zone is full");
        else
            callEffect();
    }

    private boolean isRitualSummonPossible() {
        int sumOfLevels = 0;
        for (CardTemplate cardTemplate : field.getAttackerMat().getCardList(Location.MONSTER_ZONE))
            sumOfLevels += ((MonsterCard) cardTemplate).getLevel();

        ArrayList<CardTemplate> ritualCardTemplates = new ArrayList<>();
        for (CardTemplate cardTemplate : field.getAttackerMat().getCardList(Location.HAND))
            if (cardTemplate instanceof MonsterCard)
                if (((MonsterCard) cardTemplate).getCardType() == CardType.RITUAL)
                    ritualCardTemplates.add(cardTemplate);

        if (ritualCardTemplates.isEmpty())
            return false;
        for (CardTemplate ritualCardTemplate : ritualCardTemplates)
            if (((MonsterCard) ritualCardTemplate).getLevel() > sumOfLevels)
                return false;

        return true;
    }

    private void ritualSummon() {

    }

    private boolean tributeSummonOrSet(boolean summon) {
        int level = ((MonsterCard) getSelectedCard()).getLevel();
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
            ((Monster) getSelectedCard()).setFaceUp(true);

        return true;
    }

    public void summon() {
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (selectedCardLocation != Location.HAND || !(cardTemplate instanceof MonsterCard))
            throw new GameErrorException("you can't summon this cardTemplate");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("action not allowed in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster cardTemplate zone is full");
        else if (isMonsterAddedToField)
            throw new GameErrorException("you already summoned/set on this turn");
        else if (tributeSummonOrSet(true))
            return;

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        ((Monster) cardTemplate).setFaceUp(true);
        isMonsterAddedToField = true;
    }

    public void setPosition(String position) {
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("you can't change this cardTemplate position");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (!position.equalsIgnoreCase("attack") && !position.equalsIgnoreCase("defense"))
            throw new GameErrorException("invalid position");

        Monster monster = (Monster) cardTemplate;
        if (position.equalsIgnoreCase("attack") && monster.isAttacker()
                || position.equalsIgnoreCase("defense") && !monster.isAttacker())
            throw new GameErrorException("this cardTemplate is already in the wanted position");
        else if (monster.isPositionChanged())
            throw new GameErrorException("you already changed this cardTemplate position in this turn");

        monster.setAttacker(position.equalsIgnoreCase("attack"));
        monster.setPositionChanged(true);
    }

    public void setMonster() {
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this cardTemplate");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster cardTemplate zone is full");
        else if (isMonsterAddedToField)
            throw new GameErrorException("you already summoned/set on this turn");
        else if (tributeSummonOrSet(false))
            return;

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        isMonsterAddedToField = true;
    }

    public void setSpellOrTrap() {
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (selectedCardLocation != Location.HAND)
            throw new GameErrorException("you can't set this cardTemplate");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (cardTemplate instanceof SpellTrapCard
                && ((SpellTrapCard) cardTemplate).getEffectType() == EffectType.FIELD) {
            field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.FIELD_ZONE);
            return;
        } else if (field.getAttackerMat().getCardCount(Location.SPELL_AND_TRAP_ZONE) == 5)
            throw new GameErrorException("spell cardTemplate zone is full");

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
    }

    public void showGraveyard(boolean opponent) {
        ArrayList<CardTemplate> graveyard;
        if (opponent)
            graveyard = (ArrayList<CardTemplate>) field.getDefenderMat().getCardList(Location.GRAVEYARD);
        else
            graveyard = (ArrayList<CardTemplate>) field.getAttackerMat().getCardList(Location.GRAVEYARD);

        if (graveyard.isEmpty())
            throw new GameErrorException("graveyard is empty");
        else
            DuelView.showCardListStringView(graveyard);
    }

    public void showSelectedCard() {
        // TODO check phase 1 doc, page 11 (cardTemplate show <cardTemplate name>)
        CardTemplate cardTemplate = getSelectedCard();
        if (cardTemplate == null)
            throw new GameErrorException("no cardTemplate is selected yet");
        else if (isOpponentCardSelected && !((Castable) cardTemplate).isFaceUp())
            throw new GameErrorException("cardTemplate is not visible");

        DuelView.showCardInfoStringView(cardTemplate);
    }
}