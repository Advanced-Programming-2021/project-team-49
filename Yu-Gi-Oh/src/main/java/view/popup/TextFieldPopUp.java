package view.popup;

import exception.GameErrorException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);
        vBox.setAlignment(Pos.CENTER);

        Text sentenceText = new Text(sentence);

        TextField inputField = new TextField();

        Text resultText = new Text();

        HBox hBox = new HBox();
        hBox.setSpacing(30.0);

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

        show(vBox, 150.0, 300.0);
    }
}
