package view;

import controller.Controller;
import exception.YugiohException;

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
        List<String> command = getCommandList(input, prefix);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);

        if (flags.length > 0)
            removeFlagsFromCommand(flags, command);
        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
        return arguments;
    }

    public static String[] getArguments(String[] argumentNames, String flag, String input, String prefix) {
        List<String> command = getCommandList(input, prefix);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);

        if (flag != null)
            removeFlagFromCommand(flag, command);
        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);

        return arguments;
    }

    public static String[] getArguments(String[] argumentNames, String input, String prefix) {
        List<String> command = getCommandList(input, prefix);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);

        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
        return arguments;
    }

    public static String getArgument(String argumentName, String[] flags, String input, String prefix) {
        List<String> command = getCommandList(input, prefix);
        String argument = extractArgumentFromCommand(argumentName, command);

        if (flags.length > 0)
            removeFlagsFromCommand(flags, command);
        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
        return argument;
    }

    public static String getArgument(String argumentName, String flag, String input, String prefix) {
        List<String> command = getCommandList(input, prefix);
        String argument = extractArgumentFromCommand(argumentName, command);

        if (flag != null)
            removeFlagFromCommand(flag, command);
        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
        return argument;
    }

    public static String getArgument(String argumentName, String input, String prefix) {
        List<String> command = getCommandList(input, prefix);
        String argument = extractArgumentFromCommand(argumentName, command);

        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
        return argument;
    }

    private static List<String> getCommandList(String input, String prefix) {
        return new ArrayList<>(Arrays.asList(input.substring(prefix.length() + 1).split(" ")));
    }

    private static void removeFlagsFromCommand(String[] flags, List<String> command) {
        for (String flag : flags)
            removeFlagFromCommand(flag, command);
    }

    private static void removeFlagFromCommand(String flag, List<String> command) {
        String flagString = "--" + flag;
        String abbreviatedFlagString = "-" + flag.charAt(0);

        for (int i = 0; i < command.size(); i++)
            if (flagString.equals(command.get(i))
                    || abbreviatedFlagString.equals(command.get(i))) {
                command.remove(i);
                return;
            }
    }

    private static String[] extractArgumentsFromCommand(String[] argumentNames, List<String> command) {
        String[] arguments = new String[argumentNames.length];

        for (int i = 0; i < argumentNames.length; i++)
            arguments[i] = extractArgumentFromCommand(argumentNames[i], command);
        return arguments;
    }

    private static String extractArgumentFromCommand(String argumentName, List<String> command) {
        String commandString = "--" + argumentName;
        String abbreviatedCommandString = "-" + argumentName.charAt(0);

        for (int j = 0; j < command.size() - 1; j++)
            if (commandString.equals(command.get(j))
                    || abbreviatedCommandString.equals(command.get(j))) {
                String argument = command.get(j + 1);
                command.remove(j);
                command.remove(j);
                return argument;
            }
        return null;
    }

    public static boolean[] findFlags(String[] flags, String input) {
        String[] command = input.split(" ");
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
            throw new YugiohException(INVALID_COMMAND_MESSAGE);
    }
}
