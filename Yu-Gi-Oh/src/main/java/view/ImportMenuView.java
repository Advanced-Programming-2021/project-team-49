package view;

import controller.ImportExportController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ImportMenuView extends View {

    private final ImportExportController controller = new ImportExportController();

    @FXML
    private VBox root;
    @FXML
    private Text message;

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void chooseFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        try {
            controller.importCard(file.getPath());
            message.setText("Card has been imported successfully!");
            message.setFill(Color.GREEN);
        } catch (GameErrorException exception) {
            message.setText("couldn't load file");
            message.setFill(Color.FIREBRICK);
        }
    }
}
