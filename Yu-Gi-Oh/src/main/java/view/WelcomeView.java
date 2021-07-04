package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;

public class WelcomeView extends View {

    @FXML
    private BorderPane root;
    @FXML
    private ImageView headerLogo;

    public void setListeners() {
        headerLogo.fitWidthProperty().bind(headerLogo.getScene().getWindow().widthProperty().subtract(20));
        headerLogo.setFitHeight(headerLogo.getImage().getHeight() / headerLogo.getImage().getWidth() * 780);
    }

    public void enterSignUpMenu() throws Exception {
        setCloseRequestToSave();
        enterNewMenu("/fxml/signup.fxml", root);
    }

    public void enterLoginMenu() throws Exception {
        setCloseRequestToSave();
        enterNewMenu("/fxml/login.fxml", root);
    }

    public void setCloseRequestToSave() {
        root.getScene().getWindow().setOnCloseRequest(event -> {
            try {
                Controller.save();
            } catch (IOException exception) {
                exception.printStackTrace();
                System.exit(1);
            }
        });
    }

    public void closeApplicationRaw() {
        System.exit(0);
    }
}
