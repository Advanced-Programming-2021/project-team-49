package controller;

import com.opencsv.exceptions.CsvValidationException;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;

import java.io.IOException;

public class ImportExportController extends Controller {


    public void importCard(String cardPath) {
        try {
            DATABASE.importCard(cardPath);
        } catch (IOException|CsvValidationException exception) {
            throw new GameErrorException("couldn't load file");
        }
    }

    public void exportCard(String cardName) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        if (card == null)
            throw new GameErrorException("no card exists with this name");
        else if (!(card instanceof MonsterCard))
            throw new GameErrorException("you can only save monster cards");
        else {
            try {
                DATABASE.exportCard((MonsterCard) card);
            } catch (IOException exception) {
                throw new GameErrorException("couldn't save file");
            }
        }
    }
}
