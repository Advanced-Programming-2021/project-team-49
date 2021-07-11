package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SelectOptionPopUp extends PopUp {

    private final String sentence;
    private final List<String> options;
    private final Consumer<String> consumer;

    public SelectOptionPopUp(Parent root, String sentence, List<String> options, Consumer<String> consumer) {
        super(root);
        this.sentence = sentence;
        this.options = options;
        this.consumer = consumer;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        Text sentenceText = new Text(sentence);

        VBox optionsBox = new VBox();
        optionsBox.setSpacing(10.0);
        optionsBox.setAlignment(Pos.CENTER);

        for (String option : options) {
            Button button = new Button(option);
            button.setOnMouseClicked(mouseEvent -> {
                consumer.accept(option);
                stage.close();
            });
            optionsBox.getChildren().add(button);
        }

        vBox.getChildren().addAll(sentenceText, optionsBox);

        vBox.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/popup.css")).toExternalForm());
        vBox.getStyleClass().add("root");

        show(vBox, HEIGHT, WIDTH);
    }
}
