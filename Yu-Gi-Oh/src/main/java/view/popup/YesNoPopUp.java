package view.popup;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        vBox.setPrefWidth(300.0);
        vBox.setPrefHeight(150.0);
        vBox.setAlignment(Pos.CENTER);

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

        show(vBox, 150.0, 300.0);
    }
}
