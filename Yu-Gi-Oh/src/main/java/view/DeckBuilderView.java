package view;

import controller.DeckBuilderController;
import model.card.CardTemplate;
import model.card.Monster;
import model.card.Spell;
import model.card.Trap;
import model.user.Deck;

import java.util.*;

public class DeckBuilderView extends AbstractView {
    public DeckBuilderView(DeckBuilderController controller) {
        String input = removeExtraWhitespace(INPUT_STREAM.nextLine());

        while (runCommand(controller, input))
            input = removeExtraWhitespace(INPUT_STREAM.nextLine());
    }

    private static boolean runCommand(DeckBuilderController controller, String input) {
        boolean isMenuOpen = runDefaultCommands(DeckBuilderController.TITLE, input);
        if (!isMenuOpen) {
            controller.escape();
            return false;
        }

        String[] command = input.split(" ");
        try {
            if (input.startsWith("deck create")) {
                if (input.length() < 13)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);

                String deckName = input.substring(12);
                controller.createDeck(deckName);

                System.out.println("deck created successfully!");
            } else if (input.startsWith("deck delete")) {
                if (input.length() < 13)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);

                String deckName = input.substring(12);
                controller.deleteDeck(deckName);

                System.out.println("deck deleted successfully!");
            } else if (input.startsWith("deck activate")) {
                if (input.length() < 15)
                    throw new RuntimeException(INVALID_COMMAND_MESSAGE);

                String deckName = input.substring(14);
                controller.activateDeck(deckName);

                System.out.println("deck activated successfully!");
            } else if (input.startsWith("deck add-card")) {
                String cardName = getStringValueFromCommand("card", command);
                String deckName = getStringValueFromCommand("deck", command);

                controller.addCardToDeck(cardName, deckName, !isFlagUsedInCommand("side", command));

                System.out.println("card added to deck successfully!");
            } else if (input.startsWith("deck rm-card")) {
                String cardName = getStringValueFromCommand("card", command);
                String deckName = getStringValueFromCommand("deck", command);

                controller.removeCardFromDeck(cardName, deckName, !isFlagUsedInCommand("side", command));

                System.out.println("card removed from deck successfully!");
            } else if (input.startsWith("deck show")) {
                if (input.equals("deck show --all")) {
                    System.out.println(showAllDecks(controller.getUserActiveDeck(),
                            controller.getUserDecks()));
                } else if (input.equals("deck show --cards")) {
                    System.out.println(showAllCards(controller.getOwnedCards()));
                } else {
                    String deckName = getStringValueFromCommand("deck", command);
                    boolean mainDeck = !isFlagUsedInCommand("side", command);

                    System.out.print(showDeck(deckName, mainDeck, controller.getMonsters(deckName, mainDeck),
                            controller.getSpells(deckName, mainDeck), controller.getTraps(deckName, mainDeck)));
                }
            } else
                throw new RuntimeException(INVALID_COMMAND_MESSAGE);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
        return isMenuOpen;
    }

    private static String showAllDecks(Deck activeDeck, ArrayList<Deck> decks) {
        StringBuilder allDecks = new StringBuilder("Decks:\n" +
                "Active deck:\n");
        if (activeDeck != null)
            allDecks.append(deckInfoStringView(activeDeck));

        allDecks.append("Other decks:\n");
        for (Deck deck : decks) {
            if (deck.equals(activeDeck))
                continue;
            allDecks.append(deckInfoStringView(deck));
        }
        return allDecks.toString();
    }

    private static String deckInfoStringView(Deck deck) {
        StringBuilder deckInfo = new StringBuilder();
        deckInfo.append(deck.getName()).append(": main deck ").append(deck.getMainDeckSize())
                .append(", side deck ").append(deck.getSideDeckSize());
        if (deck.isDeckValid())
            deckInfo.append(", valid\n");
        else
            deckInfo.append(", invalid\n");

        return deckInfo.toString();
    }

    private static String showAllCards(ArrayList<CardTemplate> cards) {
        StringBuilder allCards = new StringBuilder();

        for (CardTemplate card : cards)
            allCards.append(card.getName()).append(": ").append(card.getDescription()).append("\n");

        return allCards.toString();
    }

    private static String showDeck(String deckName, boolean mainDeck, ArrayList<Monster> monsters,
                                   ArrayList<Spell> spells, ArrayList<Trap> traps) {
        StringBuilder deck = new StringBuilder("Deck: " + deckName + "\n");

        if (mainDeck)
            deck.append("Main deck:\n");
        else
            deck.append("Side deck:\n");

        deck.append("Monsters:\n");
        for (Monster monster : monsters)
            deck.append(monster.getName()).append(": ").append(monster.getDescription()).append("\n");

        deck.append("Spells:\n");
        for (Spell spell : spells)
            deck.append(spell.getName()).append(": ").append(spell.getDescription()).append("\n");

        deck.append("Traps:\n");
        for (Trap trap : traps)
            deck.append(trap.getName()).append(": ").append(trap.getDescription()).append("\n");

        return deck.toString();
    }
}