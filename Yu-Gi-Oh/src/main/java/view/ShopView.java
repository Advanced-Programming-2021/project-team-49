package view;

import controller.ShopController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends AbstractView {

    private final ShopController controller;
    private final String BUY_CARD_STYLE = "shop buy (?<cardName>\\.+)";

    public ShopView(ShopController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        if(input.matches(BUY_CARD_STYLE)) {
            controller.buyCard(getCommandMatcher(input, BUY_CARD_STYLE));
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

    }
}
