package view.popup;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.cardtemplate.CardTemplate;
import view.ShopView;

public class ShopPopUp {

    private final CardTemplate card;
    private final Stage stage;
    private final Parent root;
    private final ShopView shopView;

    public ShopPopUp(CardTemplate card, Parent root, ShopView view) {
        this.stage = new Stage();
        this.card = card;
        this.root = root;
        this.shopView = view;
    }

    public void initialize() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(300);
        borderPane.setPrefHeight(380);

        Button buyButton = new Button("Buy");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(mouseEvent -> stage.close());
        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shopView.buyTheSelectedCard(card);
            }
        });
        HBox hBox = new HBox();

        hBox.getChildren().addAll(cancelButton, buyButton);
        hBox.setSpacing(15);

        borderPane.setBottom(hBox);
        hBox.setAlignment(Pos.CENTER);

        ImageView image = new ImageView(new Image(card.getCardPicPath()));
        image.setFitHeight(255);
        image.setPreserveRatio(true);
        borderPane.setCenter(image);

        show(borderPane);
    }

    public void show(Parent pane) {
        Scene popUpScene = new Scene(pane);
        stage.setScene(popUpScene);
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Window outerWindow = root.getScene().getWindow();
        Window innerWindow = popUpScene.getWindow();
        innerWindow.setX(outerWindow.getX() + (outerWindow.getWidth() / 2) - (150));
        innerWindow.setY(outerWindow.getY() + (outerWindow.getHeight() / 2) - (190));
        stage.show();
    }
}
