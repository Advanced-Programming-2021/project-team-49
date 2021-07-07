package view;

import controller.ProfileController;
import exception.GameErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class ChangePasswordView extends View {

    ProfileController controller = new ProfileController();

    @FXML
    private VBox root;

    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private TextField visibleOldPasswordField;
    @FXML
    private TextField visibleNewPasswordField;
    @FXML
    private TextField visibleConfirmNewPasswordField;

    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private TextFlow messageContainer;
    @FXML
    private Text message;

    @FXML
    private void acceptNewPassword() throws IOException {
        try {
            if (showPasswordCheckBox.isSelected())
                controller.changePassword(visibleOldPasswordField.getText(), visibleNewPasswordField.getText(),
                        visibleConfirmNewPasswordField.getText());
            else
                controller.changePassword(oldPasswordField.getText(), newPasswordField.getText(),
                        confirmNewPasswordField.getText());
            message.setText("Sign up successful");
            message.setFill(Color.GREEN);
            messageContainer.setStyle("-fx-background-color: white;");
            enterNewMenu("/fxml/profile.fxml", root);
        } catch (GameErrorException exception) {
            message.setText(exception.getMessage());
            message.setFill(Color.FIREBRICK);
            messageContainer.setStyle("-fx-background-color: white;");
        }
    }

    @FXML
    private void togglePasswordView() {
        setPasswordFieldVisibilities(showPasswordCheckBox.isSelected());

        if (showPasswordCheckBox.isSelected()) {
            visibleOldPasswordField.setText(oldPasswordField.getText());
            visibleNewPasswordField.setText(newPasswordField.getText());
            visibleConfirmNewPasswordField.setText(confirmNewPasswordField.getText());
        } else {
            oldPasswordField.setText(visibleOldPasswordField.getText());
            newPasswordField.setText(visibleNewPasswordField.getText());
            confirmNewPasswordField.setText(visibleConfirmNewPasswordField.getText());
        }
    }

    private void setPasswordFieldVisibilities(boolean visible) {
        visibleOldPasswordField.setVisible(visible);
        visibleNewPasswordField.setVisible(visible);
        visibleConfirmNewPasswordField.setVisible(visible);

        oldPasswordField.setVisible(!visible);
        newPasswordField.setVisible(!visible);
        confirmNewPasswordField.setVisible(!visible);
    }

    @FXML
    private void cancelChangePassword() throws IOException {
        enterNewMenu("/fxml/profile.fxml", root);
    }

    @FXML
    private void handleKeyInput(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER))
            acceptNewPassword();
    }
}
