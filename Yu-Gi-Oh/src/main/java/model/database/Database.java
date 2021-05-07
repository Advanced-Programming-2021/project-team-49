package model.database;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.card.*;
import model.user.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: implement loading from file
public class Database {

    private final Userbase userbase = new Userbase();
    private final Shop shop = new Shop();
    private final List<CardTemplate> cards;

    public Database() {
        cards = new ArrayList<>();
    }

    private CSVReader readFile(String address) throws IOException {
        return new CSVReader(new FileReader(address));
    }

    private void loadMonsterCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null) {
            CardTemplate card = createMonsterCard(info);
            cards.add(card);
            shop.addCard(card, Integer.parseInt(info[8]));
        }
    }

    private void loadSpellTrapCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null) {
            CardTemplate card = createSpellTrapCard(info);
            cards.add(card);
            shop.addCard(card, Integer.parseInt(info[5]));
        }
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

    private CardTemplate createMonsterCard(String[] info) {
        MonsterType monsterType = null;
        Attribute attribute = null;
        CardType cardType = null;
        Effect effect = null;

        for (MonsterType value : MonsterType.values()) {
            if (value.getMonsterType().equals(info[3])) {
                monsterType = value;
                break;
            }
        }
        // TODO if monsterType was null, throw exception

        for (Attribute value : Attribute.values()) {
            if (value.getAttribute().equals(info[2])) {
                attribute = value;
                break;
            }
        }
        // TODO if attribute was null, throw exception

        for (CardType value : CardType.values()) {
            if (value.getCardType().equals(info[4])) {
                cardType = value;
                break;
            }
        }
        // TODO if cardType was null, throw exception

        if (cardType != CardType.NORMAL) {
            for (Effect value : Effect.values()) {
                if (value.getCardName().equals(info[0])) {
                    effect = value;
                    break;
                }
            }
            // TODO if effect was null, throw exception
        } else
            effect = Effect.NONE;

        return new Monster(info[0], info[7], effect, Integer.parseInt(info[1]), attribute,
                cardType, monsterType, Integer.parseInt(info[5]), Integer.parseInt(info[6]));
    }

    private CardTemplate createSpellTrapCard(String[] info) {
        EffectType effectType = null;
        Status status = null;
        Effect effect = null;

        for (EffectType value : EffectType.values()) {
            if (value.getEffectType().equals(info[2])) {
                effectType = value;
                break;
            }
        }
        // TODO if effectType was null, throw exception

        for (Status value : Status.values()) {
            if (value.getStatus().equals(info[4])) {
                status = value;
                break;
            }
        }
        // TODO if status was null, throw exception

        for (Effect value : Effect.values()) {
            if (value.getCardName().equals(info[0])) {
                effect = value;
                break;
            }
        }
        // TODO if effect was null, throw exception

        switch (info[1]) {
            case "Trap":
                return new Trap(info[0], info[3], effect, effectType, status);

            case "Spell":
                return new Spell(info[0], info[3], effect, effectType, status);

            default:
                // TODO throw exception
                return null; // delete after adding throw exception
        }
    }
}