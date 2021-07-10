package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.WelcomeView;
import javafx.scene.media.*;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/audio/Despacito.mp3")).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        setUpScene(stage);
        setUpStage(stage);
    }

    private void setUpScene(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull
                (getClass().getResource("/fxml/welcome.fxml")));
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

        stage.getIcons().add(new Image(getClass().getResource("/image/icon.png").toExternalForm()));
        stage.show();
    }
}
