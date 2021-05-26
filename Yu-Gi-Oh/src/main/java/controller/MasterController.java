package controller;

import com.opencsv.exceptions.CsvValidationException;
import model.database.Database;

import java.io.IOException;

public class MasterController {
    private Controller nextController;
    private Database database;

    public MasterController() {
        try {
            database = new Database();
        } catch (IOException|CsvValidationException exception) {
            System.err.println("failed to load data");
            System.exit(-1);
        }
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
