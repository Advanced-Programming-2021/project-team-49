package view;

import controller.ShopController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.cardtemplate.CardTemplate;
import view.popup.ShopPopUp;

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
                rectangle.setCursor(Cursor.HAND);
                int finalI = i;
                int finalJ = j;
                rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        rectangle.setEffect(new DropShadow());
                    }
                });
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectCardToBuy(finalI, finalJ);
                    }
                });
                rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        rectangle.setEffect(null);
                    }
                });
                gridPane.add(rectangle, i, j);
            }
        }

    }

    public void selectCardToBuy(int i, int j) {
        new ShopPopUp(allCards.get(12 * j + i), root).initialize();
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }
}
