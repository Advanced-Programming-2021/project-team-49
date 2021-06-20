package controller;

import exception.GameErrorException;
import model.cardtemplate.Card;
import model.database.Database;
import model.user.User;
import view.ShopView;

import java.util.Comparator;
import java.util.List;

public class ShopController extends AbstractController {

    private final Database database;

    public ShopController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
        title = "Shop Menu";
    }

    public void run() {
        new ShopView(this).run();
    }

    public void buyCard(String cardName) {
        Card card = database.getCardByName(cardName);
        if (user.getCoins() < card.getPrice())
            throw new GameErrorException("not enough money");

        user.removeCoins(card.getPrice());
        user.addCard(card);
    }

    public Card getCard(String cardName) {
        Card card = database.getCardByName(cardName);
        if (card == null)
            throw new GameErrorException("there is no card with this name");
        return card;
    }

    public List<Card> getSortedCards() {
        database.getCards().sort(Comparator.comparing(Card::getName));
        return database.getCards();
    }
}
