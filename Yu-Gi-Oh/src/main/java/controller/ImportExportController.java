package controller;

import model.database.Database;
import model.user.User;
import view.ImportExportView;

public class ImportExportController extends AbstractController {
    private final Database database;

    public ImportExportController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
        title = "Import/Export Menu";
    }

    public void run() {
        new ImportExportView(this).run();
    }
}
