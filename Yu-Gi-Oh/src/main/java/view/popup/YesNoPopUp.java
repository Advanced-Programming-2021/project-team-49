package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class YesNoPopUp extends PopUp {

    private final String question;
    private final Runnable runnable;

    public YesNoPopUp(Parent root, String question, Runnable runnable) {
        super(root);
        this.question = question;
        this.runnable = runnable;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        Text questionText = new Text(question);

        HBox hBox = new HBox();
        hBox.setSpacing(30.0);
        hBox.setAlignment(Pos.CENTER);

        Button noButton = new Button("No");
        noButton.setOnMouseClicked(mouseEvent -> stage.close());

        Button yesButton = new Button("Yes");
        yesButton.setOnMouseClicked(mouseEvent -> {
            runnable.run();
            stage.close();
        });

        hBox.getChildren().addAll(noButton, yesButton);
        vBox.getChildren().addAll(questionText, hBox);

        vBox.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/popup.css")).toExternalForm());
        vBox.getStyleClass().add("root");
        questionText.getStyleClass().add("prompt-text");

        show(vBox, HEIGHT, WIDTH);
    }
}
