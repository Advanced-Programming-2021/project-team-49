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
import view.DeckBuilderView;

public class RenamePopUp extends PopUp {

    private final DeckBuilderController controller;
    private final DeckBuilderView.DeckData deckData;

    public RenamePopUp(Parent root, DeckBuilderView.DeckData deckData, DeckBuilderController controller) {
        super(root);
        this.deckData = deckData;
        this.controller = controller;
    }

    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);
        vBox.setAlignment(Pos.CENTER);

        TextField newNameField = new TextField();
        newNameField.setPromptText("Enter a name...");

        Text resultText = new Text();

        HBox hBox = new HBox();
        hBox.setSpacing(30.0);

        Button exitButton = new Button("Back");
        exitButton.setOnMouseClicked(mouseEvent -> stage.close());

        Button renameButton = new Button("Rename");
        renameButton.setOnMouseClicked(mouseEvent -> {
            String newName = newNameField.getText();
            if (newName == null || newName.equals(""))
                return;
            try {
                controller.renameDeck(newName, deckData.getDeckName());
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
                return;
            }
            deckData.setDeckName(newName);
            resultText.setText("Deck name changed successfully!");
        });

        hBox.getChildren().addAll(exitButton, renameButton);

        vBox.getChildren().addAll(newNameField, resultText, hBox);

        show(vBox, 150.0, 300.0);
    }
}
