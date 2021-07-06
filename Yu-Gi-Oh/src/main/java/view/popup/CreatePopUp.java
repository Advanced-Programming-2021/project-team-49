package view.popup;

import controller.DeckBuilderController;
import exception.GameErrorException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CreatePopUp extends PopUp {

    private final DeckBuilderController controller;

    public CreatePopUp(Parent root, DeckBuilderController controller) {
        super(root);
        this.controller = controller;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);
        vBox.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter a name...");

        Text resultText = new Text();

        HBox hBox = new HBox();
        hBox.setSpacing(30.0);

        Button exitButton = new Button("Back");
        exitButton.setOnMouseClicked(mouseEvent -> stage.close());

        Button createButton = new Button("Create");
        createButton.setOnMouseClicked(mouseEvent -> {
            String name = nameField.getText();
            if (name == null || name.equals(""))
                return;
            try {
                controller.createDeck(name);
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
                return;
            }
            resultText.setText("Deck created successfully!");
        });

        hBox.getChildren().addAll(exitButton, createButton);

        vBox.getChildren().addAll(nameField, resultText, hBox);

        show(vBox, 150.0, 300.0);
    }
}
