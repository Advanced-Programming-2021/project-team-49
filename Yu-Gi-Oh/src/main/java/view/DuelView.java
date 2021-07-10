package view;

import controller.DuelController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.cardtemplate.CardTemplate;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Card;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DuelView extends View {

    private static final Image UNKNOWN_CARD = new Image(Objects.requireNonNull(
            View.class.getResource("/cards/Unknown.jpg")).toExternalForm());

    private final DuelController controller;

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

        cardImage.setOnMouseClicked(mouseEvent -> {
            // TODO main idea of handling is a bit tricky
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                //controller.summon();
                // TODO summon monster, activate spell, set trap
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                // TODO set monster, set spell
            }
        });

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