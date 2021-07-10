package view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DuelMenuView extends View{

    @FXML
    private BorderPane root;


    public void backToMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void enterTwoPlayerDuel() throws IOException {
        enterNewMenu("/fxml/twoplayerduel.fxml", root);
    }
}
