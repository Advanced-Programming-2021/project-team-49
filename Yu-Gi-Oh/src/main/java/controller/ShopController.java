package controller;

import model.database.Shop;
import model.user.User;
import view.ShopView;

public class ShopController extends AbstractController {

    private final Shop shop;

    public ShopController(MasterController masterController, User user, Shop shop) {
        super(masterController, user);
        this.shop = shop;
        title = "Shop Menu";
    }

    public void run() {
        new ShopView(this).run();
    }

    public void buyCard(String cardName) {

    }

    @Override
    public String toString() {
        return "";
    }
}
