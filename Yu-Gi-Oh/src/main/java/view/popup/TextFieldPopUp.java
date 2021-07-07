package view.popup;

import exception.GameErrorException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.function.Function;

public class TextFieldPopUp extends PopUp {

    private final String sentence;
    private final String choice;
    private final Function<String, String> choiceFunction;

    public TextFieldPopUp(Parent root, String sentence, String choice, Function<String, String> choiceFunction) {
        super(root);
        this.sentence = sentence;
        this.choice = choice;
        this.choiceFunction = choiceFunction;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Text sentenceText = new Text(sentence);

        TextField inputField = new TextField();
        inputField.setMaxWidth(300);

        Text resultText = new Text();

        HBox hBox = new HBox();
        hBox.setSpacing(120.0);
        hBox.setAlignment(Pos.CENTER);

        Button exitButton = new Button("Cancel");
        exitButton.setOnMouseClicked(mouseEvent -> stage.close());

        Button choiceButton = new Button(choice);
        choiceButton.setOnMouseClicked(mouseEvent -> {
            try {
                resultText.setText(choiceFunction.apply(inputField.getText()));
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
            }
        });

        hBox.getChildren().addAll(exitButton, choiceButton);
        vBox.getChildren().addAll(sentenceText, inputField, resultText, hBox);

        vBox.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/popup.css")).toExternalForm());
        vBox.getStyleClass().add("root");
        sentenceText.getStyleClass().add("prompt-text");

        show(vBox, 200.0, WIDTH);
    }
}
