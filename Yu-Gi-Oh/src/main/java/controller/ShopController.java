package controller;

import exception.GameErrorException;
import model.cardtemplate.CardTemplate;


import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ShopController extends Controller {

    public int getUserCoins() {
        return user.getCoins();
    }

    public int getOwnedCardCount(CardTemplate card) {
        Map<CardTemplate, Integer> ownedCards = user.getOwnedCardsMap();
        if (ownedCards.containsKey(card))
            return ownedCards.get(card);
        return 0;
    }

    public void buyCard(String cardName) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        if (user.getCoins() < card.getPrice())
            throw new GameErrorException("not enough money");
        user.removeCoins(card.getPrice());
        user.addCard(card);
    }

    public CardTemplate getCard(String cardName) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        if (card == null)
            throw new GameErrorException("there is no card with this name");
        return card;
    }

    public List<CardTemplate> getSortedCards() {
        DATABASE.getCards().sort(Comparator.comparing(CardTemplate::getName));
        return DATABASE.getCards();
    }

    public void increaseUserBalance(int amount) {
        user.addCoins(amount);
    }
}
