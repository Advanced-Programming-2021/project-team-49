package controller;

import model.database.Database;

public class MasterController implements Controller {
    private Controller nextController;
    private Database database;

    public MasterController() {
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
