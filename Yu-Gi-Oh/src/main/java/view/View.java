package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class View {

    protected static Stage stage;

    public void setStage(Stage stage) {
        View.stage = stage;
    }

    public static void enterNewMenu(String urlString, Node oldRoot) throws IOException {
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(View.class.getResource(urlString)));
        oldRoot.getScene().setRoot(newRoot);
    }

    public static void enterWelcomeMenu(Parent oldRoot) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                View.class.getResource("/fxml/welcome.fxml")));
        Parent newRoot = loader.load();
        WelcomeView welcomeView = loader.getController();

        oldRoot.getScene().setRoot(newRoot);
        welcomeView.setListeners();
    }

    @FXML
    public void closeApplication() {
        try {
            Controller.save();
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
