package view;

import controller.DeckBuilderController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.user.Deck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DeckView extends View {

    private final DeckBuilderController controller = new DeckBuilderController();

    public ImageView sampleImage;


    private Deck deck;
    private Map<CardTemplate, Integer> ownedCards;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Label description;
    @FXML
    private ListView<CardTemplate> cards;


    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void initialize() {
        ownedCards = controller.getOwnedCardsCopyMap();

        cards.setItems(FXCollections.observableArrayList(controller.getOwnedCardsList()));

        cards.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CardTemplate> call(ListView<CardTemplate> hBoxListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CardTemplate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null)
                            setGraphic(getCardHBoxView(item));
                    }
                };
            }
        });


        sampleImage.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.MOVE));

        sampleImage.setOnDragDropped(dragEvent -> {
            Dragboard dragboard = dragEvent.getDragboard();

            sampleImage.setImage(dragboard.getImage());

            dragEvent.setDropCompleted(true);

            dragEvent.consume();
        });
    }

    public void enterDeckMenu() throws IOException {
        enterNewMenu("/fxml/deckbuilder.fxml", root);
    }

    public HBox getCardHBoxView(CardTemplate card) {
        HBox hBox = new HBox();
        StackPane stackPane = new StackPane();
        ImageView cardImage;
        Text cardCountText = new Text();
        VBox vBox = new VBox();
        Text nameText = new Text();
        HBox descriptionBox = new HBox();
        Text powerText = new Text();
        Text levelText = new Text();

        cardCountText.setText(String.valueOf(ownedCards.get(card)));
        cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitHeight(80);
        cardImage.setPreserveRatio(true);

        nameText.setText(card.getName());

        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
        cardCountText.setStyle("-fx-fill: white;" +
                "-fx-stroke: black");
        cardCountText.setTranslateX(-5);

        if (card instanceof MonsterCard) {
            MonsterCard monster = (MonsterCard) card;
            powerText.setText(monster.getBaseAttack() + "/" + monster.getBaseDefense());
            levelText.setText("LVL: " + monster.getLevel());
        }

        descriptionBox.setSpacing(15);
        descriptionBox.getChildren().addAll(powerText, levelText);
        vBox.getChildren().addAll(nameText, descriptionBox);
        stackPane.getChildren().addAll(cardImage, cardCountText);
        hBox.setSpacing(15);
        hBox.getChildren().addAll(stackPane, vBox);

        cardImage.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = cardImage.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putImage(cardImage.getImage());

            dragboard.setContent(content);

            mouseEvent.consume();
        });

        cardImage.setOnDragDone(dragEvent -> {
            if (dragEvent.getTransferMode() != TransferMode.MOVE)
                return;

            if (ownedCards.get(card) <= 1) {
                ownedCards.remove(card);
                refreshOwnedCards();
            } else {
                ownedCards.replace(card, ownedCards.get(card) - 1);
                cardCountText.setText(String.valueOf(ownedCards.get(card)));
            }
            dragEvent.consume();
        });

        hBox.setOnMouseEntered(mouseEvent -> {
            image.setImage(cardImage.getImage());
            description.setText(card.getDescription());
        });
        return hBox;
    }

    public void refreshOwnedCards() {
        cards.setItems(FXCollections.observableArrayList(new ArrayList<>(ownedCards.keySet())));
        cards.refresh();
    }
}
