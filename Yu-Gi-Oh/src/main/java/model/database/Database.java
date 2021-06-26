package model.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import exception.GameErrorException;
import model.cardtemplate.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<CardTemplate> cards = new ArrayList<>();

    public Database() throws IOException, CsvValidationException {
        userbase = loadUserbase();
        loadMonsterCards(readCSVFile(Objects.requireNonNull(getClass().getResource("/Monster.csv")).getPath()));
        loadSpellTrapCards(readCSVFile(
                Objects.requireNonNull(getClass().getResource("/SpellTrap.csv")).getPath()));
    }

    public Userbase getUserbase() {
        return userbase;
    }

    public List<CardTemplate> getCards() {
        return cards;
    }

    public CardTemplate getCardByName(String name) {
        for (CardTemplate card : cards) {
            if (card.getName().equalsIgnoreCase(name))
                return card;
        }
        return null;
    }

    public Userbase loadUserbase() {
        try {
            Type USERBASE_TYPE = new TypeToken<Userbase>() {}.getType();
            JsonReader reader = new JsonReader(new FileReader("savedfiles/userbase.json"));

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

        FileWriter writer = new FileWriter(file);
        writer.write(GSON.toJson(userbase));
        writer.close();
    }

    public void importCard(String path) throws IOException, CsvValidationException {
        if (path.endsWith(".csv"))
            loadMonsterCards(readCSVFile(path));
        else if (path.endsWith(".json")) {
            JsonReader reader = new JsonReader(new FileReader(path));
            while (reader.hasNext())
                cards.add(GSON.fromJson(reader, MonsterCard.class));
        } else
            throw new GameErrorException("invalid file extension");
    }

    public void exportCard(MonsterCard card) throws IOException {
        new File("savedfiles").mkdirs();
        new File("savedfiles/cards").mkdirs();
        File file = new File("savedfiles/cards/" + card.getName());
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        writer.write(GSON.toJson(card));
        writer.close();
    }

    private CSVReader readCSVFile(String path) throws IOException {
        return new CSVReaderBuilder(new FileReader(path)).withSkipLines(1).build();
    }

    private void loadMonsterCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null) {
            CardTemplate card = createMonsterCard(info);
            if (card != null)
                cards.add(card);
            else
                System.out.println(Arrays.toString(info));
        }
    }

    private void loadSpellTrapCards(CSVReader csvReader) throws IOException, CsvValidationException {
        String[] info;
        while ((info = csvReader.readNext()) != null) {
            CardTemplate card = createSpellTrapCard(info);
            if (card != null)
                cards.add(card);
            else
                System.out.println(Arrays.toString(info));
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
        if (monsterType == null)
            return null;


        for (Attribute value : Attribute.values()) {
            if (value.getAttribute().equals(info[2])) {
                attribute = value;
                break;
            }
        }
        if (attribute == null)
            return null;


        for (CardType value : CardType.values()) {
            if (value.getCardType().equals(info[4])) {
                cardType = value;
                break;
            }
        }
        if (cardType == null)
            return null;


        if (cardType == CardType.EFFECT) {
            for (Effect value : Effect.values()) {
                if (value.getCardName().equals(info[0])) {
                    effect = value;
                    break;
                }
            }
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
        if (effectType == null)
            return null;


        for (Status value : Status.values()) {
            if (value.getStatus().equals(info[4])) {
                status = value;
                break;
            }
        }
        if (status == null)
            return null;


        for (Effect value : Effect.values()) {
            if (value.getCardName().equals(info[0])) {
                effect = value;
                break;
            }
        }
        if (effect == null)
            return null;


        if (info[1].equals(SpellTrapType.TRAP.getType()))
            return new SpellTrapCard(info[0], info[3], effect, effectType,
                    status, SpellTrapType.TRAP, Integer.parseInt(info[5]));
        else
            return new SpellTrapCard(info[0], info[3], effect, effectType,
                    status, SpellTrapType.SPELL, Integer.parseInt(info[5]));
    }
}