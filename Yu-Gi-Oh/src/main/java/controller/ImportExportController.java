package controller;

import com.opencsv.exceptions.CsvValidationException;
import exception.GameErrorException;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ImportExportController extends Controller {

    public List<CardTemplate> getSortedCards() {
        DATABASE.getCards().sort(Comparator.comparing(CardTemplate::getName));
        return DATABASE.getCards();
    }

    public void importCard(String cardPath) {
        try {
            DATABASE.importCard(cardPath);
        } catch (IOException|CsvValidationException exception) {
            throw new GameErrorException("Couldn't load file");
        }
    }

    public void exportCard(String cardName) {
        CardTemplate card = DATABASE.getCardByName(cardName);
        if (card == null)
            throw new GameErrorException("No card exists with this name");
        else if (!(card instanceof MonsterCard))
            throw new GameErrorException("You can only save monster cards");
        else {
            try {
                DATABASE.exportCard((MonsterCard) card);
            } catch (IOException exception) {
                throw new GameErrorException("Couldn't save file");
            }
        }
    }
}
