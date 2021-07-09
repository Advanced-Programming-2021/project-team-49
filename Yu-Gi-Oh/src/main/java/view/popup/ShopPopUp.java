package view.popup;

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

import java.util.Objects;

public class ShopPopUp extends PopUp {

    private static final double WIDTH = 440.0;
    private static final double HEIGHT = 330.0;

    private final CardTemplate card;
    private final Runnable runnable;

    public ShopPopUp(Parent root, CardTemplate card, Runnable runnable) {
        super(root);
        this.card = card;
        this.runnable = runnable;
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
                runnable.run();
                System.out.println("Card has been bought successfully!");
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(mouseEvent -> stage.close());

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(cancelButton, buyButton);
        buttonBox.setSpacing(15);
        buttonBox.setAlignment(Pos.CENTER);

        ImageView cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitWidth(200);
        cardImage.setPreserveRatio(true);

        vBox.getChildren().addAll(nameText, priceText, resultText, buttonBox);
        hBox.getChildren().addAll(cardImage, vBox);

        hBox.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                        ("/css/popup.css")).toExternalForm());
        hBox.getStyleClass().add("root");
        nameText.getStyleClass().add("shop-text");
        priceText.getStyleClass().add("shop-text");
        resultText.getStyleClass().add("result-text");

        show(hBox, HEIGHT, WIDTH);
    }
}