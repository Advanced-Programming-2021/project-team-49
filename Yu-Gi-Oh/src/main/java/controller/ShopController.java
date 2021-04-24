package controller;

import model.user.User;

import java.util.Map;

public class ShopController extends AbstractController {
    public static final String TITLE = "Shop Menu";
    private Map<String, Integer> cardPrices;

    public ShopController(MasterController masterController, User user) {
        super(masterController, user);
    }

    public void run() {

    }

    public void buyCard(String cardName) {

    }

    public void getCardPricesFromFile() {

    }

    @Override
    public String toString() {
        return "";
    }
}
