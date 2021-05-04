package model.database;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.user.User;

import java.io.FileReader;
import java.io.IOException;

// TODO: implement loading from file
public class Database {
    private final Userbase userbase = new Userbase();
    private final Shop shop = new Shop();

    public Database() {

    }

    public static CSVReader readFile(String address) throws IOException, CsvValidationException {
        FileReader filereader = new FileReader(address);
        CSVReader csvReader = new CSVReader(filereader);
        String[] nextRecord;

        while ((nextRecord = csvReader.readNext()) != null) {
            for (String cell : nextRecord) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
        return csvReader;
    }

    public Userbase getUserbase() {
        return userbase;
    }

    public Shop getShop() {
        return shop;
    }

    public void saveUserbase(Userbase userbase) {

    }

    public void saveUser(User user) {

    }
}