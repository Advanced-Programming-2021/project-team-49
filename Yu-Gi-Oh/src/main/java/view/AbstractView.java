package view;

import controller.Controller;
import exception.GameErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractView {
    protected static final String INVALID_COMMAND_MESSAGE = "invalid command";
    protected static final String ESCAPE_COMMAND = "menu exit";
    protected static final Scanner INPUT_STREAM = new Scanner(System.in);

    public static String removeExtraWhitespace(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static String[] getArguments(String[] argumentNames, String[] flags, String input, String prefix) {
        String[] command = getCommand(input, prefix);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);
        boolean[] flagConditions = findFlags(flags, command);

        int commandCount = argumentNames.length * 2;
        for (boolean flagCondition : flagConditions)
            if (flagCondition)
                commandCount++;

        if (commandCount != command.length)
            throw new GameErrorException(INVALID_COMMAND_MESSAGE);

        return arguments;
    }

    public static String[] getArguments(String[] argumentNames, String flag, String input, String prefix) {
        return getArguments(argumentNames, new String[] {flag}, input, prefix);
    }

    public static String[] getArguments(String[] argumentNames, String input, String prefix) {
        String[] command = getCommand(input, prefix);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);

        if (command.length != 2 * arguments.length)
            throw new GameErrorException(INVALID_COMMAND_MESSAGE);

        return arguments;
    }

    public static String getArgument(String argumentName, String[] flags, String input, String prefix) {
        return getArguments(new String[] {argumentName}, flags, input, prefix)[0];
    }

    public static String getArgument(String argumentName, String flag, String input, String prefix) {
        return getArgument(argumentName, new String[] {flag}, input, prefix);
    }

    public static String getArgument(String argumentName, String input, String prefix) {
        return getArguments(new String[] {argumentName}, input, prefix)[0];
    }

    private static String[] getCommand(String input, String prefix) {
        return input.substring(prefix.length() + 1).split(" ");
    }

    private static String[] extractArgumentsFromCommand(String[] argumentNames, String[] command) {
        String[] arguments = new String[argumentNames.length];

        for (int i = 0; i < argumentNames.length; i++)
            arguments[i] = extractArgumentFromCommand(argumentNames[i], command);
        return arguments;
    }

    private static String extractArgumentFromCommand(String argumentName, String[] command) {
        String commandString = "--" + argumentName;
        String abbreviatedCommandString = "-" + argumentName.charAt(0);

        for (int j = 0; j < command.length - 1; j++)
            if (commandString.equals(command[j])
                    || abbreviatedCommandString.equals(command[j])) {
                return command[j + 1];
            }
        throw new GameErrorException(INVALID_COMMAND_MESSAGE, new NullPointerException());
    }

    public static boolean[] findFlags(String[] flags, String input) {
        return findFlags(flags, input.split(" "));
    }

    public static boolean[] findFlags(String[] flags, String[] command) {
        boolean[] isFlagFound = new boolean[flags.length];

        for (int i = 0; i < flags.length; i++)
            isFlagFound[i] = isFlagUsedInCommand(flags[i], command);
        return isFlagFound;
    }

    public static boolean isFlagUsedInCommand(String flag, String input) {
        String[] command = input.split(" ");
        return isFlagUsedInCommand(flag, command);
    }

    public static boolean isFlagUsedInCommand(String flag, String[] command) {
        String flagString = "--" + flag;
        String abbreviatedFlagString = "-" + flag.charAt(0);

        for (String segment : command)
            if (flagString.equals(segment) || abbreviatedFlagString.equals(segment))
                return true;
        return false;
    }

    protected static boolean runDefaultCommands(String input, Controller controller) {
        if (input.startsWith("menu enter")) {
            System.out.println("menu navigation is not possible");
            return true;
        } else if (input.equals("menu show-current")) {
            System.out.println(controller.getTitle());
            return true;
        } else if (input.equals(ESCAPE_COMMAND)) {
            controller.escape();
            return false;
        } else
            throw new GameErrorException(INVALID_COMMAND_MESSAGE);
    }
}
