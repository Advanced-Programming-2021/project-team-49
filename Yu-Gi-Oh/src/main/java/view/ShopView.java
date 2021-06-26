package view;

import controller.ShopController;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.cardtemplate.SpellTrapCard;

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
            else if (input.startsWith("increase "))
                controller.increaseUserBalance(Integer.parseInt(input.substring(9)));
            else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("invalid value entered as a number");
        } catch (Exception exception) {
            System.out.println(INVALID_COMMAND_MESSAGE);
        }
        return true;
    }

    private static void showAllCards(ShopController controller) {
        for (CardTemplate card : controller.getSortedCards())
            System.out.println(card.getName() + ": " + card.getPrice());
    }

    private static void showCard(ShopController controller, String cardName) {
        CardTemplate card = controller.getCard(cardName);
        StringBuilder cardInfo = new StringBuilder();
        if (card instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) card;
            cardInfo.append("Name: ").append(monsterCard.getName()).append("\n")
                    .append("Level: ").append(monsterCard.getLevel()).append("\n")
                    .append("Type: ").append(monsterCard.getMonsterType()).append("\n")
                    .append("ATK: ").append(monsterCard.getBaseAttack()).append("\n")
                    .append("DEF: ").append(monsterCard.getBaseDefense()).append("\n")
                    .append("Price: ").append(monsterCard.getPrice()).append("\n")
                    .append("Description: ").append(monsterCard.getDescription());
        } else {
            SpellTrapCard spellTrapCard = (SpellTrapCard) card;
            cardInfo.append("Name: ").append(spellTrapCard.getName()).append("\n")
                    .append(spellTrapCard.getType().getType()).append("\n")
                    .append("Type: ").append(spellTrapCard.getEffectType()).append("\n")
                    .append("Price: ").append(spellTrapCard.getPrice()).append("\n")
                    .append("Description: ").append(spellTrapCard.getDescription());
        }
        System.out.println(cardInfo);
    }
}
