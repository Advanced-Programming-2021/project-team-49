package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CreateCardView extends View{

    @FXML
    private VBox root;
    @FXML
    private CheckBox types;

    public void initialize() {

    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }
}
