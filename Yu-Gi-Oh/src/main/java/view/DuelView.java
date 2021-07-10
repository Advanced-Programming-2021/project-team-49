package view;

import controller.DuelController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.cardtemplate.CardTemplate;
import model.game.card.Card;

import java.io.IOException;
import java.util.List;

public class DuelView extends View {

    private static final Image UNKNOWN_CARD = new Image(View.class.getResource("/cards/Unknown.jpg").toExternalForm());


    private DuelController controller;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Text description;

    // TODO public modifier for accessing from another stage
    public GridPane phaseButtonPane;
    public Pane fieldPane;
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

    public void initialize() {
        for (int i = 0; i < 6; i++) {
            defenderHand.add(createCardInHandImage(null, true), i, 0);
            attackerHand.add(createCardInHandImage(null, true), i, 0);
        }
        for (int i = 0; i < 5; i++) {
            defenderSpellZone.add(createCardInSpellZoneImage(null, true, true), i, 0);
            attackerSpellZone.add(createCardInSpellZoneImage(null, true, true), i, 0);
        }
        defenderMonsterZone.add(createCardInMonsterZoneImage(null, true, true, true), 0, 0);
        defenderMonsterZone.add(createCardInMonsterZoneImage(null, true, true, false), 1, 0);
        defenderMonsterZone.add(createCardInMonsterZoneImage(null, true, true, true), 2, 0);
        defenderMonsterZone.add(createCardInMonsterZoneImage(null, true, true, false), 3, 0);
        defenderMonsterZone.add(createCardInMonsterZoneImage(null, true, true, true), 4, 0);
        for (int i = 0; i < 5; i++) {
            attackerMonsterZone.add(createCardInMonsterZoneImage(null, true, true, false), i, 0);

        }
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

    public ImageView createCardImage(CardTemplate card, int width, boolean hide) {
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

    public ImageView createCardInHandImage(CardTemplate card, boolean opponent) {
        ImageView cardImage = createCardImage(card, 95, opponent);

        if (opponent)
            cardImage.setRotate(180.0);

        cardImage.setOnMouseClicked(mouseEvent -> {
            // TODO main idea of handling is a bit tricky
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                // TODO summon monster, activate spell, set trap
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                // TODO set monster, set spell
            }
        });

        return cardImage;
    }

    public ImageView createCardInSpellZoneImage(CardTemplate card, boolean opponent, boolean hide) {
        ImageView cardImage = createCardImage(card, 55, hide);

        if (opponent)
            cardImage.setRotate(180.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }

    public ImageView createCardInMonsterZoneImage(CardTemplate card, boolean opponent, boolean hide, boolean horizontal) {
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