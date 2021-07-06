package view;

import controller.ScoreboardController;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.user.User;

import java.io.IOException;
import java.util.List;

public class ScoreboardView extends View{

    private final ScoreboardController controller = new ScoreboardController();
    private final List<List<User>> scoreboard = controller.getScoreboard();


    @FXML
    private VBox root;


    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }
}
