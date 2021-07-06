package view.popup;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class DialogPopUp {

    private final Stage stage;
    private final Parent root;

    public DialogPopUp(Parent root) {
        stage = new Stage();
        this.root = root;
    }
}
