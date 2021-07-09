package view;

import controller.DuelController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.cardtemplate.CardTemplate;
import model.game.card.Card;

import java.io.IOException;
import java.util.List;

public class DuelView extends View {

    private DuelController controller;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Text description;

    // TODO public modifier for accecing from another stage
    public VBox phaseButtonBox;
    public Pane defenderSidePane;
    public VBox fieldBox;
    public GridPane defenderHand;
    public GridPane defenderSpellZone;
    public GridPane defenderMonsterZone;
    public GridPane attackerMonsterZone;
    public GridPane attackerSpellZone;
    public GridPane attackerHand;
    public Pane attackerSidePane;

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
}