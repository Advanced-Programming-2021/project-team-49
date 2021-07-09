package view.popup;

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

public class ShopPopUp extends PopUp{

    private final CardTemplate card;
    private final Runnable runnable;

    public ShopPopUp(Parent root, CardTemplate card, Runnable runnable) {
        super(root);
        this.card = card;
        this.runnable = runnable;
    }

    public void initialize() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(300);
        borderPane.setPrefHeight(380);

        Button buyButton = new Button("Buy");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(mouseEvent -> stage.close());
        buyButton.setOnMouseClicked(mouseEvent -> runnable.run());
        HBox hBox = new HBox();

        hBox.getChildren().addAll(cancelButton, buyButton);
        hBox.setSpacing(15);

        borderPane.setBottom(hBox);
        hBox.setAlignment(Pos.TOP_CENTER);

        ImageView image = new ImageView(new Image(card.getCardPicPath()));
        image.setFitHeight(255);
        image.setPreserveRatio(true);
        borderPane.setCenter(image);

        VBox vBox = new VBox();
        Text name = new Text("Name : " + card.getName());
        Text description = new Text("Description : " + card.getDescription());
        Text price = new Text("Price : " + card.getPrice());
        vBox.getChildren().addAll(name, description, price);

        borderPane.setRight(vBox);

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
