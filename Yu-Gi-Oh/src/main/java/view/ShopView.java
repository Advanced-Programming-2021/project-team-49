package view;

import controller.ShopController;
import exception.GameErrorException;
import model.cardtemplate.Card;
import model.database.Database;

public class ShopView extends AbstractView {

    private final ShopController controller;

    public ShopView(ShopController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.startsWith("shop buy"))
                controller.buyCard(input.substring(9));
            else if (input.equals("shop show --all"))
                showAllCards(controller);
            else if (input.startsWith("card show"))
                showCard(controller, input.substring(10));
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
    }

    private static void showAllCards(ShopController controller) {
        for (Card card : controller.getSortedCards())
            System.out.println(card.getName() + ": " + card.getPrice());
    }

    private static void showCard(ShopController controller, String cardName) {
        DuelView.showCardInfoStringView(controller.getCard(cardName));
    }
}
