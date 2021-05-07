package controller;

import model.database.Database;
import model.user.User;
import view.MainMenuView;

public class MainMenuController extends AbstractController {
    public static final String TITLE = "Main Menu";
    private Database database;

    public MainMenuController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
    }

    public void run() {
        new MainMenuView(this);
    }

    public void enterMenu(String menuTitle) {
        Controller nextController;
        switch (menuTitle) {
            case "Deck":
                nextController = new DeckBuilderController(masterController, user);
                break;
            case "Scoreboard":
                nextController = new ScoreboardController(masterController, user, database.getUserbase());
                break;
            case "Profile":
                nextController = new ProfileController(masterController, user);
                break;
            case "Shop":
                nextController = new ShopController(masterController, user, database.getShop());
                break;
            case "Import/Export":
                nextController = new ImportExportController(masterController, user, database);
                break;
            case "Login":
                throw new RuntimeException("can't enter menu: you must logout");
            case "Duel":
                throw new RuntimeException("can't enter menu: you must start a new game");
            default:
                throw new RuntimeException("invalid menu");
        }
        masterController.setNextController(nextController);
    }

    public void startDuel(String secondPlayer) {

    }

    @Override
    public void escape() {
        LoginController loginController = new LoginController(masterController, database.getUserbase());
        masterController.setNextController(loginController);
    }
}
