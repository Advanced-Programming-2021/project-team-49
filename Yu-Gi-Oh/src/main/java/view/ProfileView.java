package view;

import controller.ProfileController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileView extends View {

    private final ProfileController controller = new ProfileController();

    @FXML
    private HBox root;

    @FXML
    private VBox profilePicView;
    @FXML
    private ImageView profilePic;

    @FXML
    private HBox nicknameBox;
    @FXML
    private HBox editNicknameBox;

    @FXML
    private Text usernameText;
    @FXML
    private Text nicknameText;
    @FXML
    private Text scoreText;

    @FXML
    private TextFlow messageContainer;
    @FXML
    private Text message;

    @FXML
    private TextField nicknameTextField;

    @FXML
    public void initialize() throws FileNotFoundException {
        usernameText.setText("Username: " + controller.getUser().getUsername());
        nicknameText.setText("Nickname: " + controller.getUser().getNickname());
        scoreText.setText("Score: " + controller.getUser().getScore());
        profilePic.setImage(new Image(new FileInputStream(controller.getUser().getProfilePicPath())));
    }

    @FXML
    private void beginEditingNickname() {
        nicknameBox.setVisible(false);
        editNicknameBox.setVisible(true);
        nicknameTextField.setText(controller.getUser().getNickname());
    }

    @FXML
    private void endEditingNickname() {
        editNicknameBox.setVisible(false);
        nicknameBox.setVisible(true);
    }

    @FXML
    private void acceptNewNickname() {
        try {
            controller.changeNickname(nicknameTextField.getText());
            message.setText("Nickname changed successfully");
            message.setFill(Color.GREEN);
            messageContainer.setStyle("-fx-background-color: white;");

            nicknameText.setText(controller.getUser().getNickname());
            endEditingNickname();
        } catch (GameErrorException exception) {
            message.setText(exception.getMessage());
            message.setFill(Color.FIREBRICK);
            messageContainer.setStyle("-fx-background-color: white;");
        }
    }

    @FXML
    private void enterChangePasswordMenu() throws IOException {
        enterNewMenu("/fxml/changepassword.fxml", root);
    }

    @FXML
    private void enterMainMenu() throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    @FXML
    private void handleKeyInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            acceptNewNickname();
    }
}
