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

        if (flags != null && flags.length > 0)
            removeFlagsFromCommand(flags, command);
        String[] arguments = extractArgumentsFromCommand(argumentNames, command);
        if (command.size() > 0)
            throw new YugiohException(INVALID_COMMAND_MESSAGE);

        return arguments;
    }

    private static List<String> getCommandList(String input, String prefix) {
        return new ArrayList<>(Arrays.asList(input.substring(prefix.length() + 1).split(" ")));
    }

    private static void removeFlagsFromCommand(String[] flags, List<String> command) {
        for (String flag : flags) {
            String flagString = "--" + flag;
            String abbreviatedFlagString = "-" + flag.charAt(0);

            for (int i = 0; i < command.size(); i++)
                if (flagString.equals(command.get(i))
                        || abbreviatedFlagString.equals(command.get(i))) {
                    command.remove(i);
                    break;
                }
        }
    }

    private static String[] extractArgumentsFromCommand(String[] argumentNames, List<String> command) {
        String[] arguments = new String[argumentNames.length];

        for (int i = 0; i < argumentNames.length; i++) {
            String commandString = "--" + argumentNames[i];
            String abbreviatedCommandString = "-" + argumentNames[i].charAt(0);

            for (int j = 0; j < command.size() - 1; j++)
                if (commandString.equals(command.get(j))
                        || abbreviatedCommandString.equals(command.get(j))) {
                    arguments[i] = command.get(j + 1);
                    command.remove(j);
                    command.remove(j + 1);
                    break;
                }
        }
        return arguments;
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
