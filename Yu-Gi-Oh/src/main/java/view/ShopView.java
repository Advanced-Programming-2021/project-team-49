package view;

import controller.ImportExportController;
import controller.ShopController;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.cardtemplate.CardTemplate;
import view.popup.ShopPopUp;

import java.io.IOException;
import java.util.List;

public class ShopView extends View {

    private final ShopController controller = new ShopController();
    private final ImportExportController exportController = new ImportExportController();

    @FXML
    private HBox root;
    @FXML
    private GridPane cardsPane;
    @FXML
    private Text coins;
    @FXML
    private ImageView image;
    @FXML
    private Label ownedCount;

    public void initialize() {
        List<CardTemplate> cards = controller.getSortedCards();
        coins.setText("\uD83D\uDCB0 " + controller.getUserCoins());

        int columnCounter = 0;
        int rowCounter = 0;
        for (CardTemplate card : cards) {
            ImageView cardImage = createCardImage(card);
            cardsPane.add(cardImage, columnCounter++, rowCounter);
            if (columnCounter > 12) {
                columnCounter = 0;
                rowCounter++;
            }
        }
    }

    public void backToMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public ImageView createCardImage(CardTemplate card) {
        ImageView cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitHeight(70);
        cardImage.setPreserveRatio(true);
        cardImage.setCursor(Cursor.HAND);

        cardImage.setOnMouseEntered(mouseEvent -> {
            cardImage.setEffect(new DropShadow());
            image.setImage(cardImage.getImage());
            ownedCount.setText("Inventory: " + controller.getOwnedCardCount(card) + " cards");
        });
        cardImage.setOnMouseExited(mouseEvent -> cardImage.setEffect(null));
        cardImage.setOnMouseClicked(mouseEvent -> new ShopPopUp(root, card,
                () -> {
            controller.buyCard(card.getName());
            coins.setText("\uD83D\uDCB0 " + controller.getUserCoins());
            ownedCount.setText("Inventory: " + controller.getOwnedCardCount(card) + " cards");
                },
                () -> {
            exportController.exportCard(card.getName());
                },
                controller).initialize());

        return cardImage;
    }
}
