package view;

import controller.DuelController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.cardtemplate.CardTemplate;
import model.game.card.Card;

import java.util.List;

public class DuelView {

    private final DuelController controller;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;

    public DuelView(DuelController controller) {
        this.controller = controller;
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

    public void run() {
    }
}