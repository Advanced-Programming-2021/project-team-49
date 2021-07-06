package view;

import controller.DeckBuilderController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.user.Deck;
import view.popup.RenamePopUp;

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

        decks.setEditable(true);

        List<TableRow<DeckData>> tableRows = new ArrayList<>();
        decks.setRowFactory(tableView -> {
            TableRow<DeckData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedDeck = row.getItem();
                    message.setText(selectedDeck.getDeckName() + " selected.");
                }
            });
            tableRows.add(row);
            return row;
        });

        decks.getColumns().add(column("Name", DeckData::getDeckNameProperty));
        decks.getColumns().add(column("Main Deck", DeckData::getMainDeckSizeProperty));
        decks.getColumns().add(column("Side Deck", DeckData::getSideDeckSizeProperty));

//        TableColumn<DeckData, String> deckName = new TableColumn<>("Name");
//        deckName.setMinWidth(100);
//        deckName.setCellValueFactory(new PropertyValueFactory<>("deckName"));
//
//        TableColumn<DeckData, Integer> mainDeckSize = new TableColumn<>("Main Deck");
//        mainDeckSize.setMinWidth(100);
//        mainDeckSize.setCellValueFactory(new PropertyValueFactory<>("mainDeckSize"));
//
//        TableColumn<DeckData, Integer> sideDeckSize = new TableColumn<>("Side Deck");
//        sideDeckSize.setMinWidth(100);
//        sideDeckSize.setCellValueFactory(new PropertyValueFactory<>("sideDeckSize"));

        decks.setItems(getDecksData());

    }

    public void run() {
    }

    public void enterMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void enterDeckMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull
                (getClass().getResource("/fxml/welcome.fxml")));
        Parent newRoot = loader.load();
        DeckView deckView = loader.getController();
        deckView.setDeck(controller.getDeckByName(selectedDeck.getDeckName()));
        root.getScene().setRoot(newRoot);

        //TODO
    }

    public void createNewDeck(MouseEvent mouseEvent) {
    }

    public void activateDeck(MouseEvent mouseEvent) {
    }

    public void renameDeck() {
        RenamePopUp renamePopUp = new RenamePopUp(root, selectedDeck, controller);
        renamePopUp.initialize();

        decks.refresh();
    }

    public void editDeck(MouseEvent mouseEvent) throws IOException {
        // TODO selected deck != null and show popup and pass deck to deckView or controller
        enterDeckMenu();
    }

    public void deleteDeck(MouseEvent mouseEvent) {
    }

    private ObservableList<DeckData> getDecksData() {
        List<DeckData> decksData = new ArrayList<>();
        for (Deck deck : controller.getUserDecks())
            decksData.add(new DeckData(deck.getName(), deck.getMainDeckSize(), deck.getSideDeckSize()));
        return FXCollections.observableArrayList(decksData);
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

        private DeckData(String deckName, int mainDeckSize, int sideDeckSize) {
            this.deckName = new SimpleStringProperty(deckName);
            this.mainDeckSize = new SimpleIntegerProperty(mainDeckSize);
            this.sideDeckSize = new SimpleIntegerProperty(sideDeckSize);
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

        public String getDeckName() {
            return deckName.get();
        }

        public int getMainDeckSize() {
            return mainDeckSize.get();
        }

        public int getSideDeckSize() {
            return sideDeckSize.get();
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
    }

    //        Callback<TableColumn, TableCell> cellFactory =
//                new Callback<TableColumn, TableCell>() {
//                    public TableCell call(TableColumn p) {
//                        return new EditingCell();
//                    }
//                };
//
//        TableColumn firstNameCol = new TableColumn("First Name");
//        firstNameCol.setMinWidth(100);
//        firstNameCol.setCellValueFactory(
//                new PropertyValueFactory<DeckData, String>("firstName"));
//        firstNameCol.setCellFactory(cellFactory);
//        firstNameCol.setOnEditCommit(
//                new EventHandler<CellEditEvent<Person, String>>() {
//                    @Override
//                    public void handle(CellEditEvent<Person, String> t) {
//                        ((Person) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setFirstName(t.getNewValue());
//                    }
//                }
//        );
}