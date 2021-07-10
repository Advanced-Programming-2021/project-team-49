package view;

import controller.DuelController;
import controller.MainMenuController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class TwoPlayerDuelView extends View {

    private MainMenuController controller = new MainMenuController();

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

    private ToggleGroup roundCountToggleGroup;

    public void initialize() {
        oneRoundButton.setToggleGroup(roundCountToggleGroup);
        threeRoundsButton.setToggleGroup(roundCountToggleGroup);
    }

    public void openDuelMenu() throws IOException {
        enterNewMenu("/fxml/duelmenu.fxml", root);
    }

    public void startDuel() {
        DuelController duelController;
        try {
            RadioButton selectedRoundCountButton = (RadioButton) roundCountToggleGroup.getSelectedToggle();
            int roundCount = Integer.parseInt(selectedRoundCountButton.getText());
            duelController = controller.startPlayerDuel(secondPlayerUsernameField.getText(), roundCount);
        } catch (GameErrorException exception) {

        }
    }

    @FXML
    private void handleKeyInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            startDuel();
    }
}
