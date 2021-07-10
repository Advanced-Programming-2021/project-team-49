package view;

import controller.DuelController;
import controller.MainMenuController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TwoPlayerDuelView extends View {

    private final MainMenuController controller = new MainMenuController();

    private final ToggleGroup roundCountToggleGroup = new ToggleGroup();

    @FXML
    private VBox root;
    @FXML
    private TextField secondPlayerUsernameField;
    @FXML
    private RadioButton oneRoundButton;
    @FXML
    private RadioButton threeRoundsButton;
    @FXML
    private Text errorMessage;

    @FXML
    private void initialize() {
        oneRoundButton.setSelected(true);
        oneRoundButton.setToggleGroup(roundCountToggleGroup);
        threeRoundsButton.setToggleGroup(roundCountToggleGroup);
    }

    public void enterDuelMenu() throws IOException {
        enterNewMenu("/fxml/duelmenu.fxml", root);
    }

    public void startDuel() throws IOException {
        try {
            RadioButton selectedRoundCountButton = (RadioButton) roundCountToggleGroup.getSelectedToggle();
            int roundCount = Integer.parseInt(selectedRoundCountButton.getText());
            DuelController duelController = controller.startPlayerDuel(secondPlayerUsernameField.getText(), roundCount);

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull
                    (getClass().getResource("/fxml/duel.fxml")));
            loader.setController(new DuelView(duelController));
            Parent newRoot = loader.load();
            root.getScene().setRoot(newRoot);

            loader = new FXMLLoader(Objects.requireNonNull
                    (getClass().getResource("/fxml/duel.fxml")));
            loader.setController(new DuelView(duelController));
            newRoot = loader.load();
            setUpStage(newRoot);
        } catch (GameErrorException exception) {
            errorMessage.setText(exception.getMessage());
        }
    }

    private void setUpStage(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Yu-Gi-Oh!");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);

        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResource("/image/icon.png")).toExternalForm()));
        stage.show();
    }

    @FXML
    private void handleKeyInput(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            startDuel();
    }
}
