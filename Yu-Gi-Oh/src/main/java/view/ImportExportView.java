package view;

import controller.ImportExportController;
import exception.GameErrorException;

public class ImportExportView extends AbstractView {

    private final ImportExportController controller;

    public ImportExportView(ImportExportController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        try {
            if (input.startsWith("import card ")) {
                controller.importCard(input.substring("import card ".length()));
                System.out.println("card imported successfully!");
            } else if (input.startsWith("export card ")) {
                String cardName = input.substring("export card ".length());
                controller.exportCard(cardName);
                System.out.println("card exported successfully!");
                System.out.println("card location: savedfiles/cards/" + cardName + ".json");
            }
            else
                return runDefaultCommands(input, controller);
        } catch (GameErrorException exception) {
            System.out.println(exception.getMessage());
        }
        return true;
    }
}
