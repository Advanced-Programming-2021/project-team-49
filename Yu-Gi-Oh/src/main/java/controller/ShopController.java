package controller;

import exception.GameErrorException;
import model.cardtemplate.Card;
import model.database.Database;
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
        Card card = Database.getCardByName(cardName);
        if (user.getCoins() >= shop.getPriceByCard(card)) {
            user.getActiveDeck().addCardToMainDeck(card);
            user.removeCoins(shop.getPriceByCard(card));
        } else throw new GameErrorException("You don't have enough money to buy this card");
    }

    @Override
    public String toString() {
        return "";
    }
}
