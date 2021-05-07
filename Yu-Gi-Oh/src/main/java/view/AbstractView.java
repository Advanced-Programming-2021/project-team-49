package view;

import java.util.Scanner;

public abstract class AbstractView {
    protected static final String INVALID_COMMAND_MESSAGE = "invalid command";
    protected static final String ESCAPE_COMMAND = "menu exit";
    protected static final Scanner INPUT_STREAM = new Scanner(System.in);

    public static String removeExtraWhitespace(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static String getStringValueFromCommand(String string, String[] command) {
        String commandString = "--" + string;
        String abbreviatedCommandString = "-" + string.charAt(0);

        for (int i = 0; i < command.length - 1; i++)
            if (commandString.equals(command[i]) || abbreviatedCommandString.equals(command[i]))
                return command[i + 1];
        return null;
    }

    public static boolean isFlagUsedInCommand(String flag, String[] command) {
        String flagString = "--" + flag;
        String abbreviatedFlagString = "-" + flag.charAt(0);

        for (String segment : command)
            if (flagString.equals(segment) || abbreviatedFlagString.equals(segment))
                return true;
        return false;
    }

    protected static boolean runDefaultCommands(String menuTitle, String input) {
        if (input.startsWith("menu enter"))
            System.out.println("menu navigation is not possible");
        else if (input.equals("menu show-current"))
            System.out.println(menuTitle);
        return !input.equals(ESCAPE_COMMAND);
    }
}
