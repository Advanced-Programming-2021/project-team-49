package view;

import controller.DeckBuilderController;
import exception.GameErrorException;
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
        try {
            if (input.startsWith("deck create ")) {
                String deckName = input.substring(12);

                controller.createDeck(deckName);
                System.out.println("deck created successfully!");
            } else if (input.startsWith("deck delete ")) {
                String deckName = input.substring(12);

                controller.deleteDeck(deckName);
                System.out.println("deck deleted successfully!");
            } else if (input.startsWith("deck activate ")) {
                String deckName = input.substring(14);

                controller.activateDeck(deckName);
                System.out.println("deck activated successfully!");
            } else if (input.startsWith("deck add-card")) {
                String[] arguments = getArguments(new String[] {"card", "deck"}, "side",
                        input, "deck add-card");

                controller.addCardToDeck(arguments[0], arguments[1], isFlagUsedInCommand("side", input));
                System.out.println("card added to deck successfully!");
            } else if (input.startsWith("deck rm-card")) {
                String[] arguments = getArguments(new String[] {"card", "deck"}, "side",
                        input, "deck rm-card");

                controller.removeCardFromDeck(arguments[0], arguments[1], isFlagUsedInCommand("side", input));
                System.out.println("card removed from deck successfully!");
            } else if (input.equals("deck show --all") || input.equals("deck show -a")) {
                System.out.println(showAllDecks(controller.getUserActiveDeck(), controller.getUserDecks()));
            } else if (input.equals("deck show --cards") || input.equals("deck show -c")) {
                System.out.println(showAllCards(controller.getOwnedCards()));
            } else if (input.startsWith("deck show")) {
                String deckName = getArgument("deck", "side", input, "deck show");

                System.out.print(showDeck(deckName, isFlagUsedInCommand( "side", input),
                        controller.getMonsters(deckName, isFlagUsedInCommand( "side", input)),
                        controller.getSpells(deckName, isFlagUsedInCommand( "side", input)),
                        controller.getTraps(deckName, isFlagUsedInCommand( "side", input))));
            } else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
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

    private static String showDeck(String deckName, boolean sideDeck, ArrayList<Monster> monsters,
                                   ArrayList<Spell> spells, ArrayList<Trap> traps) {
        StringBuilder deck = new StringBuilder("Deck: " + deckName + "\n");

        if (!sideDeck)
            deck.append("Side deck:\n");
        else
            deck.append("Main deck:\n");

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