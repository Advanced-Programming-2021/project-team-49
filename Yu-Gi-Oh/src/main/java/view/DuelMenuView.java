package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class DuelMenuView extends View{

    @FXML
    private BorderPane root;
    @FXML
    private ImageView headerLogo;


    public void backToMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void startDuel() throws IOException {
        enterNewMenu("/fxml/duel.fxml", root);
    }
}
