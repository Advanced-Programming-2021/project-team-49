package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DialogPopUp extends PopUp {

    private final String dialog;

    public DialogPopUp(Parent root, String dialog) {
        super(root);
        this.dialog = dialog;
    }

    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);
        vBox.setAlignment(Pos.CENTER);

        Text text = new Text(dialog);

        Button okButton = new Button("Ok");
        okButton.setOnMouseClicked(mouseEvent -> stage.close());

        vBox.getChildren().addAll(text, okButton);

        show(vBox, 150.0, 300.0);
    }
}
