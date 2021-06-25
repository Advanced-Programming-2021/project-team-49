package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.cardtemplate.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {

    private static final Gson GSON;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        GSON = builder.create();
    }
    private final Userbase userbase;
    private  final List<CardTemplate> cards = new ArrayList<>();

    public Database() throws IOException, CsvValidationException {
        userbase = loadUserbase();
        loadMonsterCards(readCSVFile(Objects.requireNonNull(getClass().getResource("Monster.csv")).getPath()));
        loadSpellTrapCards(readCSVFile(
                Objects.requireNonNull(getClass().getResource("SpellTrap.csv")).getPath()));
    }

    public Userbase getUserbase() {
        return userbase;
    }

    public List<CardTemplate> getCards() {
        return cards;
    }

    public CardTemplate getCardByName(String name) {
        for (CardTemplate card : cards) {
            if (card.getName().equals(name))
                return card;
        }
        return null;

    }

    public Userbase loadUserbase() {
        try {
            Type USERBASE_TYPE = new TypeToken<Userbase>() {}.getType();
            JsonReader reader = new JsonReader(new FileReader("userbase.json"));

            if (!reader.hasNext())
                return new Userbase();
            else
                return GSON.fromJson(reader, USERBASE_TYPE);
        } catch (IOException exception) {
            return new Userbase();
        }
    }

    public void saveUserbase() throws IOException {
        new File("savedfiles").mkdirs();
        File file = new File("savedfiles/userbase.json");
        file.createNewFile();

        FileWriter writer = new FileWriter("savedfiles/userbase.json");
        writer.write(GSON.toJson(userbase));
        writer.close();
    }

    private CSVReader readCSVFile(String address) throws IOException {
        // TODO remove first line of csv files
        return new CSVReader(new FileReader(address));
    }

    private void loadMonsterCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null)
            try {
                cards.add(createMonsterCard(info));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
    }

    private void loadSpellTrapCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null)
            try {
                cards.add(createSpellTrapCard(info));
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
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

        return new MonsterCard(info[0], info[7], effect, Integer.parseInt(info[1]), attribute, cardType,
                monsterType, Integer.parseInt(info[5]), Integer.parseInt(info[6]), Integer.parseInt(info[8]));
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

        if (info[1].equals(SpellTrapType.TRAP.getType()))
            return new SpellTrapCard(info[0], info[3], effect, effectType,
                    status, SpellTrapType.TRAP, Integer.parseInt(info[5]));
        else
            return new SpellTrapCard(info[0], info[3], effect, effectType,
                    status, SpellTrapType.SPELL, Integer.parseInt(info[5]));
    }
}