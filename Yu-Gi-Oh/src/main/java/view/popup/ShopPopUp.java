package view.popup;

import controller.ShopController;
import exception.GameErrorException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.cardtemplate.CardTemplate;

import java.io.IOException;
import java.util.Objects;

public class ShopPopUp extends PopUp {

    private static final double WIDTH = 440.0;
    private static final double HEIGHT = 330.0;

    private final CardTemplate card;
    private final Runnable buyRunnable;
    private final Runnable exportRunnable;
    private final ShopController controller;

    public ShopPopUp(Parent root, CardTemplate card, Runnable buyRunnable, Runnable exportRunnable,
                     ShopController controller) {
        super(root);
        this.card = card;
        this.buyRunnable = buyRunnable;
        this.exportRunnable = exportRunnable;
        this.controller = controller;
    }

    public void initialize() {
        HBox hBox = new HBox();
        hBox.setPrefWidth(WIDTH);
        hBox.setPrefHeight(HEIGHT);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);

        Text nameText = new Text("Name: " + card.getName());
        nameText.setWrappingWidth(220.0);
        Text priceText = new Text("Price: " + card.getPrice());
        Text resultText = new Text("");

        Button buyButton = new Button("Buy");
        buyButton.setOnMouseClicked(mouseEvent -> {
            try {
                buyRunnable.run();
                resultText.setText(("Card has been bought successfully!"));
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
            }
        });
        Button exportCardButton = new Button("Export");
        exportCardButton.setOnMouseClicked(mouseEvent -> {
            try {
                exportRunnable.run();
                resultText.setText("Card has been exported successfully");
            } catch (GameErrorException exception) {
                resultText.setText("couldn't save file");
            }

        });
        Button cancelButton = new Button("Back");
        cancelButton.setOnMouseClicked(mouseEvent -> stage.close());

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(exportCardButton, buyButton);
        buttonBox.setSpacing(8);
        buttonBox.setAlignment(Pos.CENTER);

        HBox cancelBox = new HBox();
        cancelBox.getChildren().add(cancelButton);
        cancelBox.setAlignment(Pos.CENTER);

        ImageView cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitWidth(200);
        cardImage.setPreserveRatio(true);

        vBox.getChildren().addAll(nameText, priceText, resultText, buttonBox, cancelBox);
        hBox.getChildren().addAll(cardImage, vBox);

        hBox.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                        ("/css/popup.css")).toExternalForm());
        hBox.getStyleClass().add("root");
        nameText.getStyleClass().add("shop-text");
        priceText.getStyleClass().add("shop-text");
        resultText.getStyleClass().add("result-text");

        if (card.getPrice() > controller.getUserCoins()) {
            buyButton.setDisable(true);
        }
        show(hBox, HEIGHT, WIDTH);
    }
}