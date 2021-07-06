package view.popup;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public abstract class PopUp {

    protected final Stage stage;
    protected final Parent root;

    public PopUp(Parent root) {
        stage = new Stage();
        this.root = root;
    }

    public abstract void initialize();

    public void show(Parent parent, double parentHeight, double parentWidth) {
        Scene popUpScene = new Scene(parent);
        stage.setScene(popUpScene);
        stage.initOwner(root.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        Window outerWindow = root.getScene().getWindow();
        Window innerWindow = popUpScene.getWindow();
        innerWindow.setX(outerWindow.getX() + (outerWindow.getWidth() / 2) - (parentWidth / 2));
        innerWindow.setY(outerWindow.getY() + (outerWindow.getHeight() / 2) - (parentHeight / 2));
        stage.show();
    }
}
