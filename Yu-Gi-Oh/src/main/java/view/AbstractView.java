package view;

public abstract class AbstractView {
    protected static final String INVALID_COMMAND_MESSAGE = "invalid command";
    protected static final String ESCAPE_COMMAND = "menu exit";

    public static String removeExtraWhitespace(String string) {
        return string.trim().replaceAll("\\s+", " ");
    }

    public static String getStringValueFromCommand(String string, String[] command) {
        String commandString = "--" + string;
        String abbreviatedCommandString = "-" + string.charAt(0);

        for (int i = 0; i < command.length - 1; i++)
            if (commandString.equals(command[i])
                    || abbreviatedCommandString.equals(command[i]))
                return command[i + 1];
        return null;
    }

    protected static boolean runDefaultCommands(String menuTitle, String input) {
        if (input.startsWith("menu enter"))
            System.out.println("menu navigation is not possible");
        else if (input.equals("menu show-current"))
            System.out.println(menuTitle);
        return !input.equals(ESCAPE_COMMAND);
    }
}
