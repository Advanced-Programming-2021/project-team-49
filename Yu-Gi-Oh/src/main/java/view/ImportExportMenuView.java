package view;

import controller.ImportExportController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ImportExportMenuView extends View {

    private final ImportExportController controller = new ImportExportController();

    @FXML
    private BorderPane root;

    public void initialize() {
    }



    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }


}
