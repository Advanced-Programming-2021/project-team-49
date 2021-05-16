package controller;

import model.database.Shop;
import model.user.User;

public class ShopController extends AbstractController {
    public static final String TITLE = "Shop Menu";
    private final Shop shop;

    public ShopController(MasterController masterController, User user, Shop shop) {
        super(masterController, user);
        this.shop = shop;
    }

    public void run() {

    }

    public void buyCard(String cardName) {

    }

    @Override
    public String toString() {
        return "";
    }
}
