package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class DialogPopUp extends PopUp {

    private final String dialog;

    public DialogPopUp(Parent root, String dialog) {
        super(root);
        this.dialog = dialog;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        Text text = new Text(dialog);

        Button okButton = new Button("Ok");
        okButton.setOnMouseClicked(mouseEvent -> stage.close());

        vBox.getChildren().addAll(text, okButton);

        vBox.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/popup.css")).toExternalForm());
        vBox.getStyleClass().add("root");
        text.getStyleClass().add("prompt-text");

        show(vBox, HEIGHT, WIDTH);
    }
}
