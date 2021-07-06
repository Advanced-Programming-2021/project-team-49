package view;

import controller.LoginController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginView extends View {

    private final LoginController controller = new LoginController();
    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Text message;

    public void enterWelcomeMenu() throws IOException {
        enterWelcomeMenu(root);
    }

    @FXML
    private void login() throws IOException {
        try {
            if (showPasswordCheckBox.isSelected())
                controller.login(usernameField.getText(), visiblePasswordField.getText());
            else
                controller.login(usernameField.getText(), passwordField.getText());
            enterNewMenu("/fxml/mainmenu.fxml", root);
        } catch (GameErrorException exception) {
            message.setText(exception.getMessage());
        }
    }

    @FXML
    private void togglePasswordView() {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setVisible(false);
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setText(passwordField.getText());
        } else {
            visiblePasswordField.setVisible(false);
            passwordField.setVisible(true);
            passwordField.setText(visiblePasswordField.getText());
        }
    }

    @FXML
    private void handleKeyInput(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            login();
    }
}
