package controller;

import model.database.Database;
import model.user.User;

public class ImportExportController extends AbstractController {
    public static final String TITLE = "Import/Export Menu";
    private final Database database;

    public ImportExportController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
    }

    public void run() {

    }
}
