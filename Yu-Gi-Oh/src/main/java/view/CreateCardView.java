package view;

import controller.CreateCardController;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class CreateCardView extends View{

    private final CreateCardController controller = new CreateCardController();

    @FXML
    private Text price;
    @FXML
    private TextField nameField;
    @FXML
    private VBox root;
    @FXML
    private RadioButton monster;
    @FXML
    private RadioButton spell;
    @FXML
    private RadioButton trap;
    @FXML
    private final ToggleGroup toggleGroup = new ToggleGroup();

    public void initialize() {
        monster.setToggleGroup(toggleGroup);
        spell.setToggleGroup(toggleGroup);
        trap.setToggleGroup(toggleGroup);
        price.setText("Final price: " + controller.getPrice());
    }


    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    public void createCard(MouseEvent mouseEvent) {
        controller.createCard();
    }

    public void setMonsterType(MouseEvent mouseEvent) {
        controller.cardType("Monster");
        price.setText("Final price: " + controller.getPrice());
    }

    public void setSpellType(MouseEvent mouseEvent) {
        controller.cardType("Monster");
        price.setText("Final price: " + controller.getPrice());
    }

    public void setTrapType(MouseEvent mouseEvent) {
        controller.cardType("Monster");
        price.setText("Final price: " + controller.getPrice());
    }


    public void setName(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            controller.cardName(nameField.getText());
        }
    }
}
