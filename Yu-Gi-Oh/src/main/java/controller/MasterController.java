package controller;

import com.opencsv.exceptions.CsvValidationException;
import model.database.Database;

import java.io.IOException;

public class MasterController {
    private Controller nextController;
    private final Database database;

    public MasterController() throws IOException, CsvValidationException {
        database = new Database();
    }

    public void run() {
        nextController = new LoginController(this, database.getUserbase());
        while (nextController != null)
            nextController.run();
    }

    public void setNextController(Controller nextController) {
        this.nextController = nextController;
    }

    public Database getDatabase() {
        return this.database;
    }
}
