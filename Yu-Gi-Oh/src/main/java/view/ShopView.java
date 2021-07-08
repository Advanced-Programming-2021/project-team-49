package view;

import controller.ShopController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
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
        int columns = (int) Math.ceil(allCards.size() / 12);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < columns; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(61.4);
                rectangle.setWidth(42.1);
                if (allCards.get(12 * j + i) != null) {
                    ImagePattern image = new ImagePattern(new Image(allCards.get(12 * j + i).getCardPicPath()));
                    rectangle.setFill(image);
                }
                gridPane.add(rectangle, i, j);
            }
        }

    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }
}
