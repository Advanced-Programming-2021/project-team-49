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
    public VBox phaseButtonBox;
    public Pane defenderSidePane;
    public Pane fieldPane;
    public GridPane defenderHand;
    public GridPane defenderSpellZone;
    public GridPane defenderMonsterZone;
    public GridPane attackerMonsterZone;
    public GridPane attackerSpellZone;
    public GridPane attackerHand;
    public Pane attackerSidePane;

    public void initialize() {
        for (int i = 0; i < 6; i++) {
            defenderHand.add(createCardInHandImage(null, true), i, 0);
            attackerHand.add(createCardInHandImage(null, true), i, 0);
        }
        for (int i = 0; i < 5; i++) {
            defenderSpellZone.add(createCardInSpellZoneImage(null, true, true), i, 0);
            attackerSpellZone.add(createCardInSpellZoneImage(null, true, true), i, 0);
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

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }
}