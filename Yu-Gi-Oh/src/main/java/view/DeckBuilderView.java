package view;

import controller.DeckBuilderController;
import exception.GameErrorException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.user.Deck;
import view.popup.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class DeckBuilderView extends View {

    private final DeckBuilderController controller = new DeckBuilderController();

    @FXML
    private HBox root;
    @FXML
    private TableView<DeckData> decks;
    @FXML
    private Text message;

    private DeckData selectedDeck = null;

    public void initialize() {
        decks.setRowFactory(tableView -> {
            TableRow<DeckData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedDeck = row.getItem();
                    message.setText(selectedDeck.getDeckName() + " selected.");
                }
            });
            return row;
        });
        TableColumn<DeckData, ?> nameColumn = column("Name", DeckData::getDeckNameProperty);
        TableColumn<DeckData, ?> mainDeckColumn = column("Main Deck", DeckData::getMainDeckSizeProperty);
        TableColumn<DeckData, ?> sideDeckColumn = column("Side Deck", DeckData::getSideDeckSizeProperty);
        TableColumn<DeckData, ?> activeColumn = column("Active", DeckData::getActiveProperty);

        decks.getColumns().addAll(nameColumn, mainDeckColumn, sideDeckColumn, activeColumn);

        decks.getColumns().forEach(column -> {
            column.setResizable(false);
            column.setReorderable(false);
        });

        decks.setCursor(Cursor.HAND);
        refreshDecksData();
    }

    public void enterMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void enterDeckMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull
                (getClass().getResource("/fxml/deck.fxml")));
        Parent newRoot = loader.load();
        DeckView deckView = loader.getController();
        deckView.setDeck(controller.getDeckByName(selectedDeck.getDeckName()));
        root.getScene().setRoot(newRoot);
    }

    public void createNewDeck() {
        new TextFieldPopUp(root, "Enter a name:", "Create", name -> {
            if (name == null || name.equals(""))
                return "";
            controller.createDeck(name);
            refreshDecksData();
            return "Deck created successfully!";
        }).initialize();
    }

    public void activateDeck() {
        if (selectedDeck == null) {
            new DialogPopUp(root, "Select a deck!").initialize();
            return;
        }

        try {
            controller.activateDeck(selectedDeck.getDeckName());
            new DialogPopUp(root, "Deck activated successfully!").initialize();
            refreshDecksData();
        } catch (GameErrorException exception) {
            new DialogPopUp(root, exception.getMessage()).initialize();
        }
    }

    public void renameDeck() {
        if (selectedDeck == null) {
            new DialogPopUp(root, "Select a deck!").initialize();
            return;
        }

        new TextFieldPopUp(root, "Enter a new name:", "Rename", name -> {
            if (name == null || name.equals(""))
                return "";
            controller.renameDeck(name, selectedDeck.getDeckName());
            refreshDecksData();
            return "Deck name changed successfully!";
        }).initialize();
    }

    public void editDeck() throws IOException {
        if (selectedDeck == null) {
            new DialogPopUp(root, "Select a deck!").initialize();
            return;
        }

        enterDeckMenu();
    }

    public void deleteDeck() {
        if (selectedDeck == null) {
            new DialogPopUp(root, "Select a deck!").initialize();
            return;
        }

        String deckName = selectedDeck.getDeckName();
        new YesNoPopUp(root, "Are you sure you want to delete the Deck?",
                () -> {
            controller.deleteDeck(deckName);
            message.setText(deckName + " deleted!");
            refreshDecksData();
            selectedDeck = null;
        }).initialize();
    }

    public void refreshDecksData() {
        List<DeckData> deckDataList = new ArrayList<>();
        for (Deck deck : controller.getUserDecks()) {
            if (controller.isDeckActive(deck.getName()))
                deckDataList.add(new DeckData(deck.getName(), deck.getMainDeckSize(),
                        deck.getSideDeckSize(), "✔"));
            else
                deckDataList.add(new DeckData(deck.getName(), deck.getMainDeckSize(),
                        deck.getSideDeckSize(), "❌"));
        }
        decks.setItems(FXCollections.observableArrayList(deckDataList));
        decks.refresh();
    }

    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return column;
    }

    public static class DeckData {

        private final StringProperty deckName;
        private final IntegerProperty mainDeckSize;
        private final IntegerProperty sideDeckSize;
        private final StringProperty active;

        private DeckData(String deckName, int mainDeckSize, int sideDeckSize, String active) {
            this.deckName = new SimpleStringProperty(deckName);
            this.mainDeckSize = new SimpleIntegerProperty(mainDeckSize);
            this.sideDeckSize = new SimpleIntegerProperty(sideDeckSize);
            this.active = new SimpleStringProperty(active);
        }

        public StringProperty getDeckNameProperty() {
            return deckName;
        }

        public IntegerProperty getMainDeckSizeProperty() {
            return mainDeckSize;
        }

        public IntegerProperty getSideDeckSizeProperty() {
            return sideDeckSize;
        }

        public StringProperty getActiveProperty() {
            return active;
        }

        public String getDeckName() {
            return deckName.get();
        }

        public int getMainDeckSize() {
            return mainDeckSize.get();
        }

        public int getSideDeckSize() {
            return sideDeckSize.get();
        }

        public String isActive() {
            return active.get();
        }

        public void setDeckName(String deckName) {
            this.deckName.set(deckName);
        }

        public void setMainDeckSize(int mainDeckSize) {
            this.mainDeckSize.set(mainDeckSize);
        }

        public void setSideDeckSize(int sideDeckSize) {
            this.sideDeckSize.set(sideDeckSize);
        }

        public void setActive(String active) {
            this.active.set(active);
        }
    }
}