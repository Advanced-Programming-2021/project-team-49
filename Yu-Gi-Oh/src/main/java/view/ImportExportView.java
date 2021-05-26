package view;

import controller.ImportExportController;

public class ImportExportView extends AbstractView {

    private final ImportExportController controller;

    public ImportExportView(ImportExportController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        return true;
    }
}
