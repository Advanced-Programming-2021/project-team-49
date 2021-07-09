package view;

import controller.Controller;
import controller.DeckBuilderController;
import exception.GameErrorException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.MonsterCard;
import model.user.Deck;
import view.popup.DialogPopUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DeckView extends View {

    private final DeckBuilderController controller = new DeckBuilderController();

    private Deck deck;
    private Map<CardTemplate, Integer> ownedCards;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Text description;
    @FXML
    private ListView<CardTemplate> cards;
    @FXML
    private GridPane mainDeckPane;
    @FXML
    private GridPane sideDeckPane;

    private int mainDeckColumns = 0;
    private int mainDeckRows = 0;
    private int sideDeckColumns = 0;

    public void initialize() {
        deck = controller.getDeck();
        ownedCards = controller.getOwnedCardsCopyMap();

        Map<CardTemplate, Integer> mainDeck = deck.getMainDeck();
        for (CardTemplate card : mainDeck.keySet()) {
            for (int i = 0; i < mainDeck.get(card); i++)
                addCardToMainDeck(card);

            if (ownedCards.containsKey(card)) {
                if (ownedCards.get(card).equals(mainDeck.get(card)))
                    ownedCards.remove(card);
                else
                    ownedCards.replace(card, ownedCards.get(card) - mainDeck.get(card));
            }
        }

        Map<CardTemplate, Integer> sideDeck = deck.getSideDeck();
        for (CardTemplate card : sideDeck.keySet()) {
            for (int i = 0; i < sideDeck.get(card); i++)
                addCardToSideDeck(card);

            if (ownedCards.containsKey(card)) {
                if (ownedCards.get(card).equals(sideDeck.get(card)))
                    ownedCards.remove(card);
                else
                    ownedCards.replace(card, ownedCards.get(card) - sideDeck.get(card));
            }
        }

        cards.setItems(FXCollections.observableArrayList(new ArrayList<>(ownedCards.keySet())));

        cards.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CardTemplate> call(ListView<CardTemplate> hBoxListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CardTemplate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty)
                            setGraphic(getCardHBoxView(item));
                    }
                };
            }
        });

        cards.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != cards)
                dragEvent.acceptTransferModes(TransferMode.MOVE);

            dragEvent.consume();
        });

        cards.setOnDragDropped(dragEvent -> {

            Dragboard dragboard = dragEvent.getDragboard();

            CardTemplate card = Controller.getDatabase().getCardByName(dragboard.getString());

            if (ownedCards.containsKey(card))
                ownedCards.replace(card, ownedCards.get(card) + 1);
            else
                ownedCards.put(card, 1);

            dragEvent.setDropCompleted(true);

            refreshOwnedCards();
            dragEvent.consume();
        });



        mainDeckPane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != mainDeckPane)
                dragEvent.acceptTransferModes(TransferMode.MOVE);

            dragEvent.consume();
        });

        mainDeckPane.setOnDragDropped(dragEvent -> {

            Dragboard dragboard = dragEvent.getDragboard();

            CardTemplate card = Controller.getDatabase().getCardByName(dragboard.getString());

            try {
                addCardToMainDeck(card);
            } catch (GameErrorException exception) {
                new DialogPopUp(root, exception.getMessage());
                return;
            }

            deck.addCardToMainDeck(card);

            dragEvent.setDropCompleted(true);

            dragEvent.consume();
        });


        sideDeckPane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != sideDeckPane)
                dragEvent.acceptTransferModes(TransferMode.MOVE);

            dragEvent.consume();
        });

        sideDeckPane.setOnDragDropped(dragEvent -> {

            Dragboard dragboard = dragEvent.getDragboard();

            CardTemplate card = Controller.getDatabase().getCardByName(dragboard.getString());

            try {
                addCardToSideDeck(card);
            } catch (GameErrorException exception) {
                new DialogPopUp(root, exception.getMessage()).initialize();
                return;
            }

            deck.addCardToSideDeck(card);


            dragEvent.setDropCompleted(true);

            dragEvent.consume();
        });
    }

    public void enterDeckMenu() throws IOException {
        controller.setDeck(null);
        enterNewMenu("/fxml/deckbuilder.fxml", root);
    }


    public void refreshOwnedCards() {
        cards.setItems(FXCollections.observableArrayList(ownedCards.keySet()));
        cards.refresh();
    }

    public void refreshMainDeckAfterRemove(int rowIndex, int columnIndex) {
        int rowCounter = rowIndex;
        int columnCounter = columnIndex;
        for (int i = 0; i < (mainDeckRows - rowIndex) * 12 + mainDeckColumns - columnIndex - 1; i++) {
            Node node = mainDeckPane.getChildren().get(rowIndex * 12 + columnIndex);
            mainDeckPane.getChildren().remove(node);
            if (columnCounter > 11) {
                columnCounter = 0;
                rowCounter++;
            }
            mainDeckPane.add(node, columnCounter++, rowCounter);
        }
        mainDeckColumns--;
        if (mainDeckColumns < 0) {
            mainDeckColumns = 11;
            mainDeckRows--;
        }
    }

    public void refreshSideDeckAfterRemove(int columnIndex) {
        int columnCounter = columnIndex;
        for (int i = 0; i < sideDeckColumns - columnIndex - 1; i++) {
            Node node = sideDeckPane.getChildren().get(columnIndex);
            sideDeckPane.getChildren().remove(node);

            sideDeckPane.add(node, columnCounter++, 0);
        }
        sideDeckColumns--;
    }

    public void addCardToMainDeck(CardTemplate card) {
        if (deck.getMainDeckSize() >= 60)
            throw new GameErrorException("Not enough space in Main Deck!");

        ImageView cardImage = createCardImage(card);

        cardImage.setOnDragDone(dragEvent -> {
            if (dragEvent.getTransferMode() != TransferMode.MOVE)
                return;

            deck.removeCardFromMainDeck(card);

            mainDeckPane.getChildren().remove(cardImage);
            refreshMainDeckAfterRemove(GridPane.getRowIndex(cardImage), GridPane.getColumnIndex(cardImage));
            dragEvent.consume();
        });

        mainDeckPane.add(cardImage, mainDeckColumns, mainDeckRows);

        mainDeckColumns++;
        if (mainDeckColumns >= 12) {
            mainDeckColumns = 0;
            mainDeckRows++;
        }
    }

    public void addCardToSideDeck(CardTemplate card) {
        if (deck.getSideDeckSize() >= 15)
            throw new GameErrorException("Not enough space in Side Deck!");

        ImageView cardImage = createCardImage(card);

        cardImage.setOnDragDone(dragEvent -> {
            if (dragEvent.getTransferMode() != TransferMode.MOVE)
                return;

            deck.removeCardFromSideDeck(card);

            sideDeckPane.getChildren().remove(cardImage);
            refreshSideDeckAfterRemove(GridPane.getColumnIndex(cardImage));
            dragEvent.consume();
        });

        sideDeckPane.add(cardImage, sideDeckColumns, 0);

        sideDeckColumns++;
    }

    public ImageView createCardImage(CardTemplate card) {
        ImageView cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitHeight(70);
        cardImage.setPreserveRatio(true);
        cardImage.setCursor(Cursor.HAND);

        cardImage.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = cardImage.startDragAndDrop(TransferMode.MOVE);

            Image image = new Image(card.getCardPicPath(), 60, 0, true, true);

            ClipboardContent content = new ClipboardContent();
            content.putString(card.getName());
            content.putImage(image);
            dragboard.setContent(content);

            mouseEvent.consume();
        });


        cardImage.setOnMouseEntered(mouseEvent -> {
            cardImage.setEffect(new DropShadow());
            image.setImage(cardImage.getImage());
            description.setText(card.getDescription());
        });

        cardImage.setOnMouseExited(mouseEvent -> cardImage.setEffect(null));

        return cardImage;
    }

    public HBox getCardHBoxView(CardTemplate card) {
        HBox hBox = new HBox();
        StackPane stackPane = new StackPane();
        ImageView cardImage;
        Text cardCountText = new Text();
        VBox vBox = new VBox();
        Text nameText = new Text();
        Text powerText = new Text();
        Text levelText = new Text();

        cardCountText.setText(String.valueOf(ownedCards.get(card)));
        cardImage = createCardImage(card);
        cardImage.setFitHeight(60);
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

        // TODO check nameText
        vBox.getChildren().addAll(powerText, levelText);
        stackPane.getChildren().addAll(cardImage, cardCountText);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(stackPane, vBox);

        cardImage.setOnDragDetected(mouseEvent -> {

            // TODO remove after debugging ListView
            if (!ownedCards.containsKey(card))
                return;

            Dragboard dragboard = cardImage.startDragAndDrop(TransferMode.MOVE);

            Image image = new Image(card.getCardPicPath(), 60, 0, true, true);

            ClipboardContent content = new ClipboardContent();
            content.putString(card.getName());
            content.putImage(image);
            dragboard.setContent(content);

            mouseEvent.consume();
        });

        cardImage.setOnDragDone(dragEvent -> {
            if (dragEvent.getTransferMode() != TransferMode.MOVE)
                return;

            if (ownedCards.get(card) <= 1) {
                ownedCards.remove(card);
                cards.getItems().remove(card);
                cards.refresh();
            } else {
                ownedCards.replace(card, ownedCards.get(card) - 1);
                cardCountText.setText(String.valueOf(ownedCards.get(card)));
            }
            dragEvent.consume();
        });

        return hBox;
    }
}
