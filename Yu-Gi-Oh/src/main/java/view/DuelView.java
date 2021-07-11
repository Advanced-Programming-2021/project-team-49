package view;

import controller.DuelController;
import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.EndPhaseException;
import exception.GameErrorException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.SpellTrapType;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;
import model.game.card.SpellTrap;
import view.popup.DialogPopUp;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DuelView extends View {

    private static final Image UNKNOWN_CARD = new Image(Objects.requireNonNull(
            View.class.getResource("/cards/Unknown.jpg")).toExternalForm());

    private final DuelController controller;
    private boolean isOpponentTurn;

    private GameMat selfMat;
    private GameMat opponentMat;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Text description;
    @FXML
    private Text opponentNicknameText;
    @FXML
    private Text selfNicknameText;
    @FXML
    private Text opponentLifePointsText;
    @FXML
    private Text selfLifePointsText;
    @FXML
    private ImageView selfProfilePic;
    @FXML
    private ImageView opponentProfilePic;

    // TODO public modifier for accessing from another stage
    public Pane fieldPane;
    public GridPane phaseButtonPane;
    public GridPane defenderHand;
    public GridPane defenderSpellZone;
    public GridPane defenderMonsterZone;
    public GridPane attackerMonsterZone;
    public GridPane attackerSpellZone;
    public GridPane attackerHand;

    public ImageView drawButton;
    public ImageView standbyButton;
    public ImageView main1Button;
    public ImageView battleButton;
    public ImageView main2Button;
    public ImageView endButton;

    public ImageView defenderFieldZone;
    public ImageView attackerFieldZone;
    public ImageView defenderGraveyard;
    public ImageView attackerGraveyard;
    public ImageView defenderDeck;
    public Text defenderDeckCount;
    public ImageView attackerDeck;
    public Text attackerDeckCount;
    public Button pauseButton;

    public DuelView(DuelController controller) {
        this.controller = controller;
    }

    public void initialize() {
        controller.getField().getAttackerMat().setDuelView(this);

        int culomn = 0;
        selfMat = controller.getField().getAttackerMat();
        for (Card card : selfMat.getCardList(Location.HAND)) {
            attackerHand.add(createCardInHandImage(card, false), culomn++, 0);
            attackerDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));
        }
        culomn = 0;
        opponentMat = controller.getField().getDefenderMat();
        for (Card card : opponentMat.getCardList(Location.HAND)) {
            defenderHand.add(createCardInHandImage(card, true), culomn++, 0);
            defenderDeckCount.setText(String.valueOf(opponentMat.getCardCount(Location.DECK)));
        }
        setPlayerData();
        controller.getField().switchMats();

        pauseButton.setOnMouseClicked(event -> {
            try {
                enterDuelMenu();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        initializePhaseButtons(drawButton, 0);
        initializePhaseButtons(standbyButton, 1);
        initializePhaseButtons(main1Button, 2);
        initializePhaseButtons(battleButton, 3);
        initializePhaseButtons(main2Button, 4);
        initializePhaseButtons(endButton, 5);
    }

    public void initializePhaseButtons(ImageView button, int phaseNumber) {
        button.setOnMouseEntered(event -> {
            if (isOpponentTurn)
                return;

            if (phaseNumber <= controller.getPhaseNumber()
                    || phaseNumber == 0 || phaseNumber == 1)
                return;

            button.setEffect(new DropShadow());
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(event -> {
            button.setEffect(null);
            button.setCursor(Cursor.DEFAULT);
        });
        button.setOnMouseClicked(event -> {
            if (isOpponentTurn)
                return;

            if (phaseNumber <= controller.getPhaseNumber()
                    || phaseNumber == 0 || phaseNumber == 1)
                return;

            try {
                controller.changePhase(phaseNumber);
            } catch (GameErrorException exception) {
                new DialogPopUp(root, exception.getMessage()).initialize();
            }
        });
    }

    public void changePhaseAutomatic() {
        try {
            controller.changePhase(0);
            Card card = controller.drawCard();
            attackerHand.add(createCardInHandImage(card, false),
                    controller.getCardCount(Location.HAND) - 1, 0);
            attackerDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));

            opponentMat.getDuelView().defenderHand.add(createCardInHandImage(card, true),
                    controller.getCardCount(Location.HAND) - 1, 0);
            opponentMat.getDuelView().defenderDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));

        } catch (EndPhaseException | GameErrorException exception) {
            new DialogPopUp(root, exception.getMessage()).initialize();
        } catch (EndOfMatchException endOfMatch) {
            // TODO close the second player stage and change root to mainmenu
        } catch (EndOfRoundException endOfRound) {
            // TODO open deck view on active deck for both players and on the button resume do next round
        }
        controller.changePhase(1);
        controller.changePhase(2);
    }

    public void setOpponentTurn(boolean opponentTurn) {
        isOpponentTurn = opponentTurn;
    }

    public static void showCardInfoStringView(Card card) {
    }

    public static void showCardListStringView(List<Card> list) {
    }

    public static void showCardNameListStringView(List<CardTemplate> list) {
    }

    public static void askForActivationHeader(String cardName, String nickName) {
    }

    public static int selectNumber(int begin, int end) {
        return 0;
    }

    public static int selectAnOption(String[] options) {
        return 0;
    }

    public static void showAttackOutcome(boolean attackedDefendingCard, int damage) {
    }

    public static void showAttackOutcome(String defendingCardName,  int damage) {
    }

    public static void showDirectAttackOutcome(int damage) {
    }

    public void enterDuelMenu() throws IOException {
        enterNewMenu("/fxml/duelmenu.fxml", root);
    }

    private void setPlayerData() {
        opponentProfilePic.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                opponentMat.getPlayer().getUser().getProfilePicResourcePath())).toExternalForm()));
        selfProfilePic.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                selfMat.getPlayer().getUser().getProfilePicResourcePath())).toExternalForm()));

        opponentNicknameText.setText(opponentMat.getPlayer().getUser().getNickname());
        selfNicknameText.setText(selfMat.getPlayer().getUser().getNickname());

        opponentLifePointsText.textProperty().bind(
                new SimpleIntegerProperty(opponentMat.getPlayer().getLifePoints()).asString());
        selfLifePointsText.textProperty().bind(
                new SimpleIntegerProperty(selfMat.getPlayer().getLifePoints()).asString());
    }

    public ImageView createCardImage(Card card, int width, boolean hide) {
        ImageView cardImage;
        if (hide)
            cardImage = new ImageView(UNKNOWN_CARD);
        else
            cardImage = new ImageView(new Image(card.getCardPicPath()));

        cardImage.setFitWidth(width);
        cardImage.setPreserveRatio(true);
        cardImage.setCursor(Cursor.HAND);

        cardImage.setOnMouseEntered(mouseEvent -> {
            cardImage.setEffect(new DropShadow());

            if (hide) {
                image.setImage(UNKNOWN_CARD);
                description.setText("");
            } else {
                image.setImage(cardImage.getImage());
                description.setText(card.getDescription());
            }

            mouseEvent.consume();
        });

        cardImage.setOnMouseExited(mouseEvent -> cardImage.setEffect(null));

        return cardImage;
    }

    public ImageView createCardInHandImage(Card card, boolean opponent) {
        ImageView cardImage = createCardImage(card, 95, opponent);

        if (opponent)
            cardImage.setRotate(180.0);

        if (!opponent) {
            cardImage.setOnMouseClicked(mouseEvent -> {
                if (isOpponentTurn)
                    return;

                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    try {
                        if (card instanceof Monster)
                            controller.summon(card);
                        else if (((SpellTrap) card).getType() == SpellTrapType.SPELL)
                            controller.activateSpell();
                        else
                            controller.setSpellOrTrap();
                    } catch (GameErrorException exception) {
                        new DialogPopUp(root, exception.getMessage()).initialize();
                    }

                    // TODO summon monster, activate spell, set trap
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    // TODO set monster, set spell
                }
            });
        }

        return cardImage;
    }

    public ImageView createCardInSpellZoneImage(Card card, boolean opponent, boolean hide) {
        ImageView cardImage = createCardImage(card, 55, hide);

        if (opponent)
            cardImage.setRotate(180.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }

    public ImageView createCardInMonsterZoneImage(Card card, boolean opponent, boolean hide, boolean horizontal) {
        ImageView cardImage = createCardImage(card, 55, hide);

        if (opponent)
            cardImage.setRotate(180.0);
        if (horizontal)
            cardImage.setRotate(90.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }
}