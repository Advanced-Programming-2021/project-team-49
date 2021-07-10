package view;

import controller.ImportExportController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ImportExportMenuView extends View {

    private final ImportExportController controller = new ImportExportController();

    @FXML
    public HBox filesToChoose;
    @FXML
    private BorderPane root;


    public void initialize() {

    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void chooseFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        controller.importCard(file.getPath());
    }
}
