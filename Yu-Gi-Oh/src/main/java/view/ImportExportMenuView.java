package view;

import controller.ImportExportController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
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
        Text resultText = new Text("");
        try {
            controller.importCard(file.getPath());
            resultText.setText("Card has been imported successfully!");
        } catch (GameErrorException exception) {
            resultText.setText("couldn't load file");
        }
        filesToChoose.getChildren().add(resultText);
    }
}
