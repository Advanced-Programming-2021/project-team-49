package view;

public abstract class AbstractView {
    protected static String getStringValueFromCommand(String string, String[] command) {
        String commandString = "--" + string;
        String abbreviatedCommandString = "-" + string.charAt(0);
        for (int i = 0; i < command.length - 1; i++)
            if (commandString.equals(command[i])
                    || commandString.equals(abbreviatedCommandString))
                return command[i + 1];
        return null;
    }
}
