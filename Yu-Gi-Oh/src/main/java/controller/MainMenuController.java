package controller;

import exception.GameErrorException;
import model.database.Database;
import model.user.User;
import view.MainMenuView;

public class MainMenuController extends AbstractController {

    private final Database database;

    public MainMenuController(MasterController masterController, User user, Database database) {
        super(masterController, user);
        this.database = database;
        title = "Main Menu";
    }

    public void run() {
        new MainMenuView(this).run();
    }

    public void enterMenu(String menuTitle) {
        Controller nextController;
        switch (menuTitle) {
            case "Deck":
                nextController = new DeckBuilderController(masterController, user, database);
                break;
            case "Scoreboard":
                nextController = new ScoreboardController(masterController, user, database.getUserbase());
                break;
            case "Profile":
                nextController = new ProfileController(masterController, user, database.getUserbase());
                break;
            case "Shop":
                nextController = new ShopController(masterController, user, database);
                break;
            case "Import/Export":
                nextController = new ImportExportController(masterController, user, database);
                break;
            case "Login":
                throw new GameErrorException("can't enter menu: you must logout");
            case "Duel":
                throw new GameErrorException("can't enter menu: you must start a new game");
            default:
                throw new GameErrorException("invalid menu");
        }
        masterController.setNextController(nextController);
    }

    public void startPlayerDuel(String secondPlayerUsername, int rounds) {
        User secondPlayer = database.getUserbase().getUserByUsername(secondPlayerUsername);

        if (secondPlayer == null)
            throw new GameErrorException("there is no player with this username");
        else if (secondPlayer.getActiveDeck() == null)
            throw new GameErrorException(secondPlayerUsername + " has no active deck");
        else if (!secondPlayer.getActiveDeck().isDeckValid())
            throw new GameErrorException(secondPlayerUsername + "'s deck is invalid");
        else if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        DuelController duelController = new DuelController(masterController, user, secondPlayer, rounds, false);
        masterController.setNextController(duelController);
    }

    public void startAIDuel(int rounds) {
        if (rounds != 1 && rounds != 3)
            System.out.println("number of rounds is not supported");

        DuelController duelController = new DuelController(masterController, user, null, rounds, true);
        masterController.setNextController(duelController);
    }

    @Override
    public void escape() {
        LoginController loginController = new LoginController(masterController, database.getUserbase());
        masterController.setNextController(loginController);
    }
}
