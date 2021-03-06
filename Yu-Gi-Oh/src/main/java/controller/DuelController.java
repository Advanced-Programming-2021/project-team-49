package controller;

import controller.effects.Event;
import controller.effects.Limit;
import controller.effects.monsters.*;
import controller.effects.spells.*;
import controller.effects.traps.*;
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
import java.util.function.Consumer;

public class DuelController extends Controller {

    private static final String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase",
            "main phase 2"};
    private static final int INIT_LIFE_POINTS = 8000;

    private final boolean hasAI;
    private final int rounds;
    private int firstTurnCounter = 2;
    private int phase = -1;
    private Field field;
    private Location selectedCardLocation = null;
    private int selectedCardPosition;
    private boolean isOpponentCardSelected;
    private boolean isMonsterAddedToField = false;

    public DuelController(User host, User guest, int rounds, boolean hasAI) {
        this.rounds = rounds;
        this.hasAI = hasAI;

        Player playerOne = new Player(host, INIT_LIFE_POINTS);
        Player playerTwo = new Player(guest, INIT_LIFE_POINTS);
        field = new Field(playerOne, playerTwo);
    }

    public Field getField() {
        return field;
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

    public void surrender() {
        endMatch(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
        throw new EndOfMatchException(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
    }

    public void endRound(Player winner, Player loser) {
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
        selectedCardLocation = null;
    }

    public Card getSelectedCard() {
        if (selectedCardLocation == null)
            return null;
        else
            return field.getAttackerMat().getCard(selectedCardLocation, selectedCardPosition);
    }

    public void setSelectedCardLocation(Location location) {
        selectedCardLocation = location;
    }

    public void changePhase(int newPhase) {
        if (newPhase <= phase)
            throw new GameErrorException("invalid phase");

        if (newPhase == 3 && firstTurnCounter > 0)
            throw new GameErrorException("You can't attack in first turn");

        phase = newPhase;

        if (phase == 0)
            field.getDefenderMat().notifyAllEffects(Event.DRAW_PHASE);

        if (phase == 1)
            field.getAttackerMat().notifyAllEffects(Event.STANDBY_PHASE);

        if (phase == 5) {
            phase = -1;
            field.getAttackerMat().notifyAllEffects(Event.END_TURN);
            field.getAttackerMat().getDuelView().setOpponentTurn(true);
            field.getDefenderMat().getDuelView().setOpponentTurn(false);
            field.switchTurn();
            selectedCardLocation = null;
            isMonsterAddedToField = false;
            if (firstTurnCounter > 0)
                firstTurnCounter--;

            field.getAttackerMat().getDuelView().changePhaseAutomatic();
        }
    }

    public Card drawCard() {
        try {
            return field.drawCard();
        } catch (EndOfRoundException exception) {
            endRound(exception.getWinner(), exception.getLoser());
            throw exception;
        }
    }

    private boolean callMonsterEffect(Monster effectCard, Monster defender) {
        if (effectCard.getEffect() == Effect.EXPLODER_DRAGON) {
            ExploderDragon exploderDragon = new ExploderDragon(1, getSelectedCard(), field, this);
            exploderDragon.setCards(defender, effectCard);
            exploderDragon.action();
            return true;
        }
        return false;
    }

    private void callSelectedCardEffect() {
        Effect effect = getSelectedCard().getEffect();

        switch (effect) {
            case NONE:
                return;

            case MONSTER_REBORN:
                new MonsterReborn(1, getSelectedCard(), field, this).action();
                break;

            case TERRAFORMING:
                new Terraforming(1, getSelectedCard(), field, this).action();
                break;

            case POT_OF_GREED:
                new PotOfGreed(1, getSelectedCard(), field, this).action();
                break;

            case RAIGEKI:
                new Raigeki(1, getSelectedCard(), field, this).action();
                break;

            case CHANGE_OF_HEART:
                new ChangeOfHeart(1, getSelectedCard(), field, this).action();
                break;

            case HARPIES_FEATHER_DUSTER:
                new HarpiesFeatherDuster(1, getSelectedCard(), field, this).action();
                break;


            case SWORDS_OF_REVEALING_LIGHT:
                new SwordsOfRevealingLight(1, getSelectedCard(), field, this).action();
                break;

            case DARK_HOLE:
                new DarkHole(1, getSelectedCard(), field, this).action();
                break;

            case SUPPLY_SQUAD:
                new SupplySquad(1, getSelectedCard(), field, this).action();
                break;

            case SPELL_ABSORPTION:
                new SpellAbsorption(1, getSelectedCard(), field, this).action();
                break;

            case MESSENGER_OF_PEACE:
                new MessengerOfPeace(1, getSelectedCard(), field, this).action();
                break;

            case YAMI:
                new Yami(1, getSelectedCard(), field, this).action();
                break;

            case FOREST:
                new Forest(1, getSelectedCard(), field, this).action();
                break;

            case CLOSED_FOREST:
                new ClosedForest(1, getSelectedCard(), field, this).action();
                break;

            case UMIIRUKA:
                new Umiiruka(1, getSelectedCard(), field, this).action();
                break;

            case SWORD_OF_DARK_DESTRUCTION:
                new SwordOfDarkDestruction(1, getSelectedCard(), field, this).action();
                break;

            case BLACK_PENDANT:
                new BlackPendant(1, getSelectedCard(), field, this).action();
                break;

            case UNITED_WE_STAND:
                new UnitedWeStand(1, getSelectedCard(), field, this).action();
                break;

            case MAGNUM_SHIELD:
                new MagnumShield(1, getSelectedCard(), field, this).action();
                break;

            case ADVANCED_RITUAL_ART:
                if (!isRitualSummonPossible())
                    throw new GameErrorException("there is no way you could ritual summon a monster");
                field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
                getSelectedCard().setFaceUp();
                break;

            case MAGIC_CYLINDER:
                new MagicCylinder(2, getSelectedCard(), field, this);
                break;

            case MIND_CRUSH:
                new MindCrush(2, getSelectedCard(), field, this);
                break;

            case MIRROR_FORCE:
                new MirrorForce(2, getSelectedCard(), field, this);
                break;

            case NEGATE_ATTACK:
                new NegateAttack(2, getSelectedCard(), field, this);
                break;

            case TIME_SEAL:
                new TimeSeal(2, getSelectedCard(), field, this);
                break;

            case TORRENTIAL_TRIBUTE:
                new TorrentialTribute(2, getSelectedCard(), field, this);
                break;

            case TRAP_HOLE:
                new TrapHole(2, getSelectedCard(), field, this);
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
        else if (((SpellTrap) card).getEffectType() == EffectType.FIELD)
            if (field.getAttackerMat().hasLimit(Limit.FIELD_SPELLS_CANT_BE_ACTIVATED))
                throw new GameErrorException("you can't activate Field Spell because of an effect");
        else
            callSelectedCardEffect();

        card.setFaceUp();
        field.getAttackerMat().notifyAllEffects(Event.A_SPELL_ACTIVATED);
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
        int ritualCardLevel = ((Monster) getSelectedCard()).getLevel();
            field.getAttackerMat().getDuelView().selectCardFromList(
                    "Select cards with sum of levels equal to " + ritualCardLevel, 10,
                    monsterCards, cards -> {
                        int sumOfLevels = 0;
                        for (Card card : cards)
                            sumOfLevels += ((Monster) card).getLevel();

                        if (sumOfLevels < ritualCardLevel)
                            throw new GameErrorException("Sum of levels don't match with ritual card");

                        nominatedCardsToTribute.addAll(cards);
                    }
            );

        for (Card card : nominatedCardsToTribute) {
            field.getAttackerMat().addCard(card, Location.GRAVEYARD);
            if (field.getAttackerMat().getCardList(Location.MONSTER_ZONE).contains(card))
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
            else
                field.getAttackerMat().moveCard(Location.HAND, card, Location.GRAVEYARD);
        }

        final String[] selected = new String[1];
        List<String> options = new ArrayList<>();
        options.add("Attacking");
        options.add("Defensive");
        field.getAttackerMat().getDuelView().selectAnOption(
                "Select card position:", options, selectedOption -> {
                    for (String option : options) {
                        if (selectedOption.equals(option))
                            selected[0] = option;
                    }
                });

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        getSelectedCard().setFaceUp();
        ((Monster) getSelectedCard()).setAttacker(selected[0].equals("Attacking"));

        selectedCardLocation = Location.MONSTER_ZONE;
        selectedCardPosition = getCardCount(Location.MONSTER_ZONE);
        field.getDefenderMat().notifyAllEffects(Event.SUMMON);
        field.getAttackerMat().notifyAllEffects(Event.SUMMON);
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

            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected, Location.GRAVEYARD);

        } else {
            if (monsterCardCount < 2)
                throw new GameErrorException("there are not enough cards for tribute");

            int selected1 = DuelView.selectNumber(1, monsterCardCount);
            int selected2 = DuelView.selectNumber(1, monsterCardCount);
            if (selected1 == -1 || selected2 == -1 || selected1 == selected2)
                throw new GameErrorException("there is no monster on one of these addresses");

            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected1, Location.GRAVEYARD);
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, selected2, Location.GRAVEYARD);
        }
        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        isMonsterAddedToField = true;
        if (summon)
            getSelectedCard().setFaceUp();

        selectedCardLocation = Location.MONSTER_ZONE;
        selectedCardPosition = getCardCount(Location.MONSTER_ZONE);
        field.getDefenderMat().notifyAllEffects(Event.SUMMON);
        field.getAttackerMat().notifyAllEffects(Event.SUMMON);
        return true;
    }

    public boolean specialSummon() {
        Effect effect = getSelectedCard().getEffect();

        switch (effect) {
            case GATE_GUARDIAN:
                new GateGuardian(1, getSelectedCard(), field, this).action();
                return true;

            case BEAST_KING_BARBAROS:
                BeastKingBarbaros effectCard = new BeastKingBarbaros(1, getSelectedCard(), field, this);
                effectCard.action();
                field.getAttackerMat().notifyFieldZoneEffect();
                return effectCard.isSpecialSummoned();

            case THE_TRICKY:
                new TheTricky(1, getSelectedCard(), field, this).action();
                field.getAttackerMat().notifyFieldZoneEffect();
                return true;

            default:
                return false;
        }
    }

    public void flipSummon() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (card.getEffect() == Effect.MAN_EATER_BUG) {
            ManEaterBug manEaterBug = new ManEaterBug(1, getSelectedCard(), field, this);
            manEaterBug.action();
        }
        else if (selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("you can't change this card position");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (card.isFaceUp() || ((Monster) card).isAttacker())
            throw new GameErrorException("you can't flip summon this card");

        card.setFaceUp();
        ((Monster) card).setAttacker(true);
        field.getDefenderMat().notifyAllEffects(Event.SUMMON);
        field.getAttackerMat().notifyAllEffects(Event.SUMMON);
        field.getDefenderMat().notifyAllEffects(Event.FLIP_SUMMON);
        callSelectedCardEffect();
    }

    public void summon() {
        Card card = getSelectedCard();

        if (!(card instanceof Monster))
            throw new GameErrorException("you can't summon this card" + card.getName());
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("action not allowed in this phase");
        else if (field.getAttackerMat().getCardCount(Location.MONSTER_ZONE) == 5)
            throw new GameErrorException("monster card zone is full");
        else if (isMonsterAddedToField)
            throw new GameErrorException("you already summoned/set on this turn");
        else if (specialSummon())
            return;
        else if (ritualSummon())
            return;
        else if (tributeSummonOrSet(true))
            return;

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.MONSTER_ZONE);
        ((Monster) card).setAttacker(true);
        card.setFaceUp();
        field.getAttackerMat().notifyFieldZoneEffect();

        selectedCardLocation = Location.MONSTER_ZONE;
        selectedCardPosition = getCardCount(Location.MONSTER_ZONE);
        field.getDefenderMat().notifyAllEffects(Event.SUMMON);
        field.getAttackerMat().notifyAllEffects(Event.SUMMON);
        field.getDefenderMat().notifyAllEffects(Event.NORMAL_SUMMON);
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
        field.getAttackerMat().notifyFieldZoneEffect();

        selectedCardLocation = Location.MONSTER_ZONE;
        selectedCardPosition = getCardCount(Location.MONSTER_ZONE);
        isMonsterAddedToField = true;
    }

    public void setSpellOrTrap() {
        Card card = getSelectedCard();
        if (card == null)
            throw new GameErrorException("no card is selected yet");
        else if (selectedCardLocation != Location.HAND || card instanceof Monster)
            throw new GameErrorException("you can't set this card");
        else if (phase != 2 && phase != 4)
            throw new GameErrorException("you can't do this action in this phase");
        else if (((SpellTrap) card).getEffectType() == EffectType.FIELD)
            throw new GameErrorException("you can't set Field Spell");
        else if (field.getAttackerMat().getCardCount(Location.SPELL_AND_TRAP_ZONE) == 5)
            throw new GameErrorException("spell card zone is full");

        field.getAttackerMat().moveCard(Location.HAND, selectedCardPosition, Location.SPELL_AND_TRAP_ZONE);
        selectedCardLocation = Location.SPELL_AND_TRAP_ZONE;
        selectedCardPosition = getCardCount(Location.SPELL_AND_TRAP_ZONE);

        if (((SpellTrap) card).getType() == SpellTrapType.TRAP)
            callSelectedCardEffect();
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

    public void checkAttackConditions() {
        if (getSelectedCard() == null)
            throw new GameErrorException("No card is selected yet");
        else if (isOpponentCardSelected || selectedCardLocation != Location.MONSTER_ZONE)
            throw new GameErrorException("You can't attack with this card");
        else if (phase != 3)
            throw new GameErrorException("You can't do this action in this phase");
        else if (((Monster) getSelectedCard()).isUsedInAttack())
            throw new GameErrorException("This card already attacked");
        else if (field.getAttackerMat().hasLimit(Limit.ALL_MONSTERS_CANT_ATTACK))
            throw new GameErrorException("You can't attack because of an effect");
    }

    public void directAttack() {
        checkAttackConditions();

        if (field.getDefenderMat().getCardCount(Location.MONSTER_ZONE) > 0)
            throw new GameErrorException("You can't attack the opponent directly");
        else {
            int damage = ((Monster) getSelectedCard()).getTotalAttack();
            ((Monster) getSelectedCard()).setUsedInAttack(true);
            field.getDefenderMat().notifyAllEffects(Event.DECLARED_ATTACK);
            field.getDefenderMat().getPlayer().removeLifePoints(damage);
            DuelView.showDirectAttackOutcome(damage);
            checkEndOfRoundWithLifePoints();
        }
    }

    public void attack(int targetPosition) throws EndOfRoundException {
        checkAttackConditions();

        Monster attacker = (Monster) getSelectedCard();
        Monster target = (Monster) field.getDefenderMat().getCard(Location.MONSTER_ZONE, targetPosition);

        attacker.setUsedInAttack(true);

        if (target == null)
            throw new GameErrorException("there is no card to attack here");

        if (attackToEffectCards(target, attacker))
            return;

        if (callMonsterEffect(target, attacker))
            return;

        field.getDefenderMat().notifyAllEffects(Event.DECLARED_ATTACK);
        attackMonster(attacker, target, selectedCardPosition, targetPosition);
    }

    public boolean attackToEffectCards(Monster target, Monster attacker) {
        switch (target.getEffect()) {
            case YOMI_SHIP:
                YomiShip yomiShip = new YomiShip(1, target, field, this);
                yomiShip.setAttacker(attacker);
                yomiShip.action();
                return true;

            case TEX_CHANGER:
                Texchanger texchanger = new Texchanger(1, target, field, this);
                texchanger.action();
                return true;

            case SUIJIN:
                field.getDefenderMat().getActivatedEffects().get(target).action();
                return true;

            case MARSHMALLON:
                Marshmallon marshmallon = new Marshmallon(1, target, field, this);
                marshmallon.setAttacker(attacker);
                marshmallon.action();
                return true;

            case EXPLODER_DRAGON:
                ExploderDragon exploderDragon = new ExploderDragon(1, target, field, this);
                exploderDragon.setCards(attacker, attacker);
                exploderDragon.action();

            default:
                return false;
        }
    }

    public void attackMonster(Monster attacker, Monster target, int attackerPosition, int targetPosition)
            throws EndOfRoundException {
        if (field.getAttackerMat().hasLimit(Limit.MONSTERS_WITH_1500_ATK_OR_MORE_CANT_ATTACK))
            if (attacker.getTotalAttack() >= 1500)
                throw new GameErrorException("you can't attack because of an effect");

        int damage;
        if (target.isAttacker()) {
            damage = attacker.getTotalAttack() - target.getTotalAttack();

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
            damage = attacker.getTotalAttack() - target.getTotalDefense();

            if (damage > 0)
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, targetPosition, Location.GRAVEYARD);
            else if (damage < 0)
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
        }

        if (!target.isAttacker() && !target.isFaceUp()) {
            field.getAttackerMat().getDuelView().showAttackOutcomeToAttacker(damage, targetPosition);
            target.setFaceUp();
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

    public void increaseLifePoints(int amount) {
        field.getAttackerMat().getPlayer().addLifePoints(amount);
    }

    public void forceWinner(String winnerNickname) {
        if (winnerNickname.equals(field.getAttackerMat().getPlayer().getUser().getNickname()))
            endMatch(field.getAttackerMat().getPlayer(), field.getDefenderMat().getPlayer());
        else if (winnerNickname.equals(field.getDefenderMat().getPlayer().getUser().getNickname()))
            endMatch(field.getDefenderMat().getPlayer(), field.getAttackerMat().getPlayer());
        else
            throw new GameErrorException(winnerNickname + " isn't the nickname of either player");
    }
}