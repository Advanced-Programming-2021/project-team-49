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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class SignUpView extends View {

    private final SignUpController controller = new SignUpController();

    @FXML
    private HBox root;
    @FXML
    private VBox profilePicView;

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
    private TextFlow messageContainer;
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
                        visiblePasswordField.getText(), visibleConfirmPasswordField.getText(),
                        ((ProfilePicView) profilePicView.getUserData()).getProfilePicPath());
            else
                controller.createUser(usernameField.getText(), nicknameField.getText(),
                        passwordField.getText(), confirmPasswordField.getText(),
                        ((ProfilePicView) profilePicView.getUserData()).getProfilePicPath());
            message.setText("Sign up successful");
            message.setFill(Color.GREEN);
            messageContainer.setStyle("-fx-background-color: white;");
            clearFields();
        } catch (GameErrorException exception) {
            message.setText(exception.getMessage());
            message.setFill(Color.FIREBRICK);
            messageContainer.setStyle("-fx-background-color: white;");
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
        setPasswordFieldVisibilities(showPasswordCheckBox.isSelected());

        if (showPasswordCheckBox.isSelected()) {
            visiblePasswordField.setText(passwordField.getText());
            visibleConfirmPasswordField.setText(confirmPasswordField.getText());
        } else {
            passwordField.setText(visiblePasswordField.getText());
            confirmPasswordField.setText(visibleConfirmPasswordField.getText());
        }
    }

    private void setPasswordFieldVisibilities(boolean visible) {
        visiblePasswordField.setVisible(visible);
        visibleConfirmPasswordField.setVisible(visible);

        passwordField.setVisible(!visible);
        confirmPasswordField.setVisible(!visible);
    }

    @FXML
    private void handleKeyInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
            createUser();
    }
}
