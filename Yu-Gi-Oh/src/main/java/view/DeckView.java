package view;

import controller.DeckBuilderController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import model.user.Deck;

import java.io.IOException;

public class DeckView extends View {

    private final DeckBuilderController controller = new DeckBuilderController();

    private Deck deck;

    @FXML
    private HBox root;

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void enterDeckMenu() throws IOException {
        enterNewMenu("/fxml/deckbuilder.fxml", root);
    }

//    public static class CardData {
//
//        private final StringProperty deckName;
//        private final IntegerProperty mainDeckSize;
//        private final IntegerProperty sideDeckSize;
//        private final StringProperty active;
//
//        private DeckData(String deckName, int mainDeckSize, int sideDeckSize, String active) {
//            this.deckName = new SimpleStringProperty(deckName);
//            this.mainDeckSize = new SimpleIntegerProperty(mainDeckSize);
//            this.sideDeckSize = new SimpleIntegerProperty(sideDeckSize);
//            this.active = new SimpleStringProperty(active);
//        }
//
//        public StringProperty getDeckNameProperty() {
//            return deckName;
//        }
//
//        public IntegerProperty getMainDeckSizeProperty() {
//            return mainDeckSize;
//        }
//
//        public IntegerProperty getSideDeckSizeProperty() {
//            return sideDeckSize;
//        }
//
//        public StringProperty getActiveProperty() {
//            return active;
//        }
//
//        public String getDeckName() {
//            return deckName.get();
//        }
//
//        public int getMainDeckSize() {
//            return mainDeckSize.get();
//        }
//
//        public int getSideDeckSize() {
//            return sideDeckSize.get();
//        }
//
//        public String isActive() {
//            return active.get();
//        }
//
//        public void setDeckName(String deckName) {
//            this.deckName.set(deckName);
//        }
//
//        public void setMainDeckSize(int mainDeckSize) {
//            this.mainDeckSize.set(mainDeckSize);
//        }
//
//        public void setSideDeckSize(int sideDeckSize) {
//            this.sideDeckSize.set(sideDeckSize);
//        }
//
//        public void setActive(String active) {
//            this.active.set(active);
//        }
//    }

}
