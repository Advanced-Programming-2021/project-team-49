package view;

import controller.Controller;
import controller.SignUpController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignUpView extends View {

    private final SignUpController controller = new SignUpController();
    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    public TextField visiblePasswordField;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    public TextField visibleConfirmPasswordField;

    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Text message;

    @FXML
    private void enterWelcomeMenu() throws IOException {
        try {
            Controller.save();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        enterWelcomeMenu(root);
    }

    @FXML
    private void createUser() {
        try {
            if (showPasswordCheckBox.isSelected())
                controller.createUser(usernameField.getText(), nicknameField.getText(),
                        visiblePasswordField.getText(), visibleConfirmPasswordField.getText());
            else
                controller.createUser(usernameField.getText(), nicknameField.getText(),
                        passwordField.getText(), confirmPasswordField.getText());
            message.setText("Sign up successful");
            message.setFill(Color.GREEN);
            clearFields();
        } catch (GameErrorException exception) {
            message.setText(exception.getMessage());
            message.setFill(Color.FIREBRICK);
        }
    }

    private void clearFields() {
        usernameField.clear();
        nicknameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        visiblePasswordField.clear();
        visibleConfirmPasswordField.clear();
    }

    @FXML
    private void togglePasswordView() {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setVisible(false);
            confirmPasswordField.setVisible(false);

            visiblePasswordField.setVisible(true);
            visibleConfirmPasswordField.setVisible(true);

            visiblePasswordField.setText(passwordField.getText());
            visibleConfirmPasswordField.setText(confirmPasswordField.getText());
        } else {
            visiblePasswordField.setVisible(false);
            visibleConfirmPasswordField.setVisible(false);

            passwordField.setVisible(true);
            confirmPasswordField.setVisible(true);

            passwordField.setText(visiblePasswordField.getText());
            confirmPasswordField.setText(visibleConfirmPasswordField.getText());
        }
    }

    @FXML
    private void handleKeyInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            createUser();
    }
}
