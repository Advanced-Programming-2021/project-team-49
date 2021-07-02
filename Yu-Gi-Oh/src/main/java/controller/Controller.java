package controller;

import com.opencsv.exceptions.CsvValidationException;
import model.database.Database;
import model.user.User;

import java.io.IOException;

public class Controller {

    protected static final Database DATABASE;
    static {
        Database database = null;
        try {
            database = new Database();
        } catch (IOException|CsvValidationException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
        DATABASE = database;
    }
    protected static User user;

    public static Database getDatabase() {
        return DATABASE;
    }

    public static void save() throws IOException {
        DATABASE.save();
    }
}
