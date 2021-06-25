package controller;

import com.opencsv.exceptions.CsvValidationException;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.database.Database;
import model.user.User;
import view.ImportExportView;

import java.io.IOException;

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

    public void importCard(String cardPath) {
        try {
            database.importCard(cardPath);
        } catch (IOException|CsvValidationException exception) {
            throw new GameErrorException("couldn't load file");
        }
    }

    public void exportCard(String cardName) {
        CardTemplate card = database.getCardByName(cardName);
        if (card == null)
            throw new GameErrorException("no card exists with this name");
        else if (!(card instanceof MonsterCard))
            throw new GameErrorException("you can only save monster cards");
        else {
            try {
                database.exportCard((MonsterCard) card);
            } catch (IOException exception) {
                throw new GameErrorException("couldn't save file");
            }
        }
    }
}
