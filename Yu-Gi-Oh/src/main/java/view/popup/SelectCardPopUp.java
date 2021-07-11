package view.popup;

import exception.GameErrorException;
import javafx.animation.Animation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.game.card.Card;
import view.DuelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectCardPopUp extends PopUp {

    private static final double WIDTH = 520.0;
    private static final double HEIGHT = 390.0;

    private final String sentence;
    private final int selectCount;
    private final List<Card> cards;
    private final Consumer<List<Card>> consumer;
    private final List<Card> selectedCards = new ArrayList<>();

    public SelectCardPopUp(Parent root, String sentence, int selectCount,
                           List<Card> cards, Consumer<List<Card>> consumer) {
        super(root);
        this.sentence = sentence;
        this.selectCount = selectCount;
        this.cards = cards;
        this.consumer = consumer;
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Text sentenceText = new Text(sentence);
        sentenceText.setWrappingWidth(420.0);

        GridPane cardPane = new GridPane();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setHgap(-5);
        for (int i = 0; i < cards.size(); i++)
            cardPane.add(createCardImage(cards.get(i)), i, 0);

        Text resultText = new Text("");

        Button doneButton = new Button("Done");
        doneButton.setOnMouseClicked(mouseEvent -> {
            if (selectedCards.size() < selectCount)
                return;

            try {
                consumer.accept(selectedCards);
                stage.close();
            } catch (GameErrorException exception) {
                resultText.setText(exception.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnMouseClicked(mouseEvent -> stage.close());

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(cancelButton, doneButton);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(sentenceText, cardPane, resultText, buttonBox);

        vBox.getStylesheets().add(Objects.requireNonNull(getClass().getResource
                ("/css/popup.css")).toExternalForm());
        vBox.getStyleClass().add("root");


        show(vBox, HEIGHT, WIDTH);
    }

    public ImageView createCardImage(Card card) {
        ImageView cardImage = new ImageView(new Image(card.getCardPicPath()));
        cardImage.setFitWidth(70);
        cardImage.setPreserveRatio(true);
        cardImage.setCursor(Cursor.HAND);

        cardImage.setOnMouseEntered(event -> cardImage.setEffect(new DropShadow()));
        cardImage.setOnMouseExited(event -> cardImage.setEffect(null));

        Animation slideDownAnimation = DuelView.getSlideCardAnimation(cardImage, 0);
        Animation slideUpAnimation = DuelView.getSlideCardAnimation(cardImage, -20);

        cardImage.setOnMouseClicked(mouseEvent -> {
            if (selectedCards.contains(card)) {
                selectedCards.remove(card);
                slideUpAnimation.stop();
                slideDownAnimation.play();
            } else if (selectedCards.size() < selectCount) {
                    selectedCards.add(card);
                    slideDownAnimation.stop();
                    slideUpAnimation.play();
            }
            mouseEvent.consume();
        });

        return cardImage;
    }
}
