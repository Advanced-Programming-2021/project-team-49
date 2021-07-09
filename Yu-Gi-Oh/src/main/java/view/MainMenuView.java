package view;

import controller.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainMenuView extends View {

    private final MainMenuController controller = new MainMenuController();
    @FXML
    private VBox root;

    public void run() {
    }

    public void enterDuelMenu() throws IOException {
        enterNewMenu("/fxml/duelmenu.fxml", root);
    }

    public void enterDeckMenu() throws IOException {
        enterNewMenu("/fxml/deckbuilder.fxml", root);
    }

    public void enterShopMenu() throws IOException {
        enterNewMenu("/fxml/shopmenu.fxml", root);
    }

    public void enterProfile() throws IOException {
        enterNewMenu("/fxml/profile.fxml", root);
    }

    public void enterScoreboard() throws IOException {
        enterNewMenu("/fxml/scoreboard.fxml", root);
    }

    public void enterImportExportMenu() throws IOException {
        enterNewMenu("/fxml/importexportmenu.fxml", root);
    }

    @FXML
    private void logout() throws IOException {
        controller.logout();
        enterWelcomeMenu(root);
    }
}
