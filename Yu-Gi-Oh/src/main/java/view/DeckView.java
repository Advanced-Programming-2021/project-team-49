package view;

import controller.DeckBuilderController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.user.Deck;

import java.io.IOException;

public class DeckView extends View {

    private final DeckBuilderController controller = new DeckBuilderController();

    private Deck deck;

    @FXML
    private VBox root;

    public void run() {
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void enterDeckMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/deckbuilder.fxml", root);
    }


}
