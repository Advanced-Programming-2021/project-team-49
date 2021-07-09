package view;

import controller.DuelController;
import javafx.scene.layout.AnchorPane;
import model.cardtemplate.CardTemplate;
import model.game.card.Card;

import java.util.List;

public class DuelView {

    private final DuelController controller;

    public AnchorPane root;

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