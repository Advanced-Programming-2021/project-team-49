package view;

import controller.ShopController;
import model.cardtemplate.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends AbstractView {

    private final ShopController controller;

    public ShopView(ShopController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        String buyCardStyle = "shop buy (?<cardName>\\.+)";
        if(input.matches(buyCardStyle)) {
            controller.buyCard(getCommandMatcher(input, buyCardStyle));
        }
        else if (input.equals("shop show --all"))
            showAllCards();
        return true;
    }

    private String getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.group("cardName");
    }

    private void showAllCards() {
        ArrayList<Card> allCards;
        allCards = Card.getAllCards();
        Collections.sort(allCards);
        for (Card card : Card.getAllCards()) {
            System.out.println(card);
        }
    }
}
