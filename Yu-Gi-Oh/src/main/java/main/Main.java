package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.WelcomeView;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setUpScene(stage);
        setUpStage(stage);
    }

    private void setUpScene(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/welcome.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        WelcomeView welcomeView = loader.getController();

        stage.setScene(scene);
        welcomeView.setStage(stage);
        welcomeView.setListeners();
    }

    private void setUpStage(Stage stage) {
        stage.setTitle("Yu-Gi-Oh!");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
    }
}
