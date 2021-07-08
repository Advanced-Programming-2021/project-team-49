package view;

import controller.ShopController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.cardtemplate.CardTemplate;

import java.io.IOException;
import java.util.List;

public class ShopView extends View{

    private final ShopController controller = new ShopController();
    private final List<CardTemplate> allCards = controller.getSortedCards();

    @FXML
    private GridPane gridPane;
    @FXML
    private VBox root;

    public void initialize() {
        int columns = (int) Math.ceil(allCards.size() / 8);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(61.4);
                rectangle.setWidth(42.1);
                gridPane.add(rectangle, i, j);
            }
        }
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }
}
