package view.popup;

import controller.DeckBuilderController;
import exception.GameErrorException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.DeckBuilderView;

public class RenamePopUp {

    private final DeckBuilderController controller;
    private final DeckBuilderView.DeckData deckData;
    private final Stage stage;
    private final Parent root;

    public RenamePopUp(Parent root, DeckBuilderView.DeckData deckData, DeckBuilderController controller) {
        stage = new Stage();
        this.root = root;
        this.deckData = deckData;
        this.controller = controller;
    }

    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);

        TextField newNameField = new TextField();
        newNameField.setPromptText("Enter a name...");

        Text resultText = new Text();

        HBox hBox = new HBox();
        hBox.setSpacing(15.0);

        Button exitButton = new Button("Back");
        exitButton.setAlignment(Pos.CENTER);
        exitButton.setOnMouseClicked(mouseEvent -> stage.close());

        Button renameButton = new Button("Rename");
        renameButton.setAlignment(Pos.CENTER);
        renameButton.setOnMouseClicked(mouseEvent -> {
            String newName = newNameField.getText();
            try {
                checkRename(newName);
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
                return;
            }
            controller.getDeckByName(deckData.getDeckName()).setName(newName);
            deckData.setDeckName(newName);
            resultText.setText("Deck name changed successfully!");
        });

        hBox.getChildren().addAll(exitButton, renameButton);

        vBox.getChildren().addAll(newNameField, resultText, hBox);

        stage.setScene(new Scene(vBox));
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    private void checkRename(String newName) {
        if (deckData.getDeckName().equals(newName))
            throw new GameErrorException("Enter a new name!");
        if (controller.getDeckByName(newName) != null)
            throw new GameErrorException("This name is already taken!");
    }
}
