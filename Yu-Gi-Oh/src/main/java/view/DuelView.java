package view;

import controller.DuelController;
import exception.EndOfMatchException;
import exception.EndOfRoundException;
import exception.EndPhaseException;
import exception.GameErrorException;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.cardtemplate.CardTemplate;
import model.cardtemplate.EffectType;
import model.cardtemplate.SpellTrapType;
import model.game.GameMat;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;
import model.game.card.SpellTrap;
import view.popup.DialogPopUp;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DuelView extends View {

    private static final Image UNKNOWN_CARD = new Image(Objects.requireNonNull(
            View.class.getResource("/cards/Unknown.jpg")).toExternalForm());

    private final DuelController controller;
    private boolean isOpponentTurn;

    private GameMat selfMat;
    private GameMat opponentMat;

    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private Text description;

    // public modifier for accessing from another stage
    public Text opponentNicknameText;
    public Text selfNicknameText;
    public Text opponentLifePointsText;
    public Text selfLifePointsText;
    public ImageView selfProfilePic;
    public ImageView opponentProfilePic;

    public Pane fieldPane;
    public GridPane phaseButtonPane;
    public GridPane defenderHand;
    public GridPane defenderSpellZone;
    public GridPane defenderMonsterZone;
    public GridPane attackerMonsterZone;
    public GridPane attackerSpellZone;
    public GridPane attackerHand;

    public ImageView drawButton;
    public ImageView standbyButton;
    public ImageView main1Button;
    public ImageView battleButton;
    public ImageView main2Button;
    public ImageView endButton;

    public ImageView defenderFieldZone;
    public ImageView attackerFieldZone;
    public ImageView defenderGraveyard;
    public ImageView attackerGraveyard;
    public ImageView defenderDeck;
    public Text defenderDeckCount;
    public ImageView attackerDeck;
    public Text attackerDeckCount;
    public Button pauseButton;

    public DuelView(DuelController controller) {
        this.controller = controller;
    }

    public void initialize() {
        controller.getField().getAttackerMat().setDuelView(this);

        int culomn = 0;
        selfMat = controller.getField().getAttackerMat();
        for (Card card : selfMat.getCardList(Location.HAND)) {
            attackerHand.add(createCardInHandImage(card, false), culomn++, 0);
            attackerDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));
        }
        culomn = 0;
        opponentMat = controller.getField().getDefenderMat();
        for (Card card : opponentMat.getCardList(Location.HAND)) {
            defenderHand.add(createCardInHandImage(card, true), culomn++, 0);
            defenderDeckCount.setText(String.valueOf(opponentMat.getCardCount(Location.DECK)));
        }
        setPlayerData();
        controller.getField().switchMats();

        attackerGraveyard.setOnMouseClicked(event -> {
            // TODO show graveyard popup
        });

        defenderGraveyard.setOnMouseClicked(event -> {
            // TODO show graveyard popup
        });

        attackerFieldZone.setOnMouseClicked(event -> {
            // TODO show card
        });

        defenderFieldZone.setOnMouseClicked(event -> {
            // TODO show card
        });

        attackerDeck.setOnMouseClicked(event -> {
            // TODO surrender
        });

        pauseButton.setOnMouseClicked(event -> {
            try {
                enterDuelMenu();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        initializePhaseButtons();
    }

    public void initializePhaseButtons() {
        initializePhaseButton(drawButton, 0);
        initializePhaseButton(standbyButton, 1);
        initializePhaseButton(main1Button, 2);
        initializePhaseButton(battleButton, 3);
        initializePhaseButton(main2Button, 4);
        initializePhaseButton(endButton, 5);
    }

    public void initializePhaseButton(ImageView button, int phaseNumber) {
        button.setOnMouseEntered(event -> {
            if (isOpponentTurn)
                return;

            if (phaseNumber <= controller.getPhaseNumber()
                    || phaseNumber == 0 || phaseNumber == 1)
                return;

            button.setEffect(new DropShadow());
            button.setCursor(Cursor.HAND);
        });
        button.setOnMouseExited(event -> {
            button.setEffect(null);
            button.setCursor(Cursor.DEFAULT);
        });
        button.setOnMouseClicked(event -> {
            if (isOpponentTurn)
                return;

            if (phaseNumber <= controller.getPhaseNumber()
                    || phaseNumber == 0 || phaseNumber == 1)
                return;

            try {
                controller.changePhase(phaseNumber);
            } catch (GameErrorException exception) {
                new DialogPopUp(root, exception.getMessage()).initialize();
            }
        });
    }

    public void changePhaseAutomatic() {
        try {
            controller.changePhase(0);
            Card card = controller.drawCard();
            attackerHand.add(createCardInHandImage(card, false),
                    controller.getCardCount(Location.HAND) - 1, 0);
            attackerDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));

            opponentMat.getDuelView().defenderHand.add(createCardInHandImage(card, true),
                    controller.getCardCount(Location.HAND) - 1, 0);
            opponentMat.getDuelView().defenderDeckCount.setText(String.valueOf(selfMat.getCardCount(Location.DECK)));

        } catch (EndPhaseException | GameErrorException exception) {
            new DialogPopUp(root, exception.getMessage()).initialize();
        } catch (EndOfMatchException endOfMatch) {
            // TODO close the second player stage and change root to mainmenu
        } catch (EndOfRoundException endOfRound) {
            // TODO open deck view on active deck for both players and on the button resume do next round
        }
        controller.changePhase(1);
        controller.changePhase(2);
    }

    public void setOpponentTurn(boolean opponentTurn) {
        isOpponentTurn = opponentTurn;
    }

    public static void showCardInfoStringView(Card card) {
    }

    public static void showCardListStringView(List<Card> list) {
    }

    public static void showCardNameListStringView(List<CardTemplate> list) {
    }

    public static void askForActivationHeader(String cardName, String nickName) {
    }

    public static int selectNumber(int begin, int end) {
        return 0;
    }

    public static int selectAnOption(String[] options) {
        return 0;
    }

    public static void showAttackOutcome(boolean attackedDefendingCard, int damage) {
    }

    public static void showAttackOutcome(String defendingCardName,  int damage) {
    }

    public static void showDirectAttackOutcome(int damage) {
    }

    public void enterDuelMenu() throws IOException {
        enterNewMenu("/fxml/duelmenu.fxml", root);
    }

    private void setPlayerData() {
        opponentProfilePic.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                opponentMat.getPlayer().getUser().getProfilePicResourcePath())).toExternalForm()));
        selfProfilePic.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                selfMat.getPlayer().getUser().getProfilePicResourcePath())).toExternalForm()));

        opponentNicknameText.setText(opponentMat.getPlayer().getUser().getNickname());
        selfNicknameText.setText(selfMat.getPlayer().getUser().getNickname());

        opponentLifePointsText.textProperty().bind(
                new SimpleIntegerProperty(opponentMat.getPlayer().getLifePoints()).asString());
        selfLifePointsText.textProperty().bind(
                new SimpleIntegerProperty(selfMat.getPlayer().getLifePoints()).asString());
    }

    public Animation getSlideCardAnimation(ImageView cardImage, int moveOffset) {
        TranslateTransition slideAnimation = new TranslateTransition();
        slideAnimation.setNode(cardImage);
        slideAnimation.setToY(moveOffset);
        slideAnimation.setDuration(Duration.millis(250));
        slideAnimation.setInterpolator(Interpolator.EASE_OUT);
        return slideAnimation;
    }

    public ImageView createCardImage(Card card, int width, boolean opponent, boolean hide) {
        ImageView cardImage;
        if (hide)
            cardImage = new ImageView(UNKNOWN_CARD);
        else
            cardImage = new ImageView(new Image(card.getCardPicPath()));

        cardImage.setFitWidth(width);
        cardImage.setPreserveRatio(true);
        cardImage.setCursor(Cursor.HAND);

        cardImage.setOnMouseEntered(mouseEvent -> {
            cardImage.setEffect(new DropShadow());

            if (opponent) {
                image.setImage(UNKNOWN_CARD);
                description.setText("");
            } else {
                image.setImage(new Image(card.getCardPicPath()));
                description.setText(card.getDescription());
            }

            mouseEvent.consume();
        });

        cardImage.setOnMouseExited(mouseEvent -> cardImage.setEffect(null));

        return cardImage;
    }

    public ImageView createCardInHandImage(Card card, boolean opponent) {
        ImageView cardImage = createCardImage(card, 95, opponent, opponent);
        Animation slideDownAnimation = getSlideCardAnimation(cardImage, 0);

        if (opponent) {
            cardImage.setRotate(180.0);

            Animation slideUpAnimation = getSlideCardAnimation(cardImage, 80);
            cardImage.setOnMouseEntered(mouseEvent -> {
                slideDownAnimation.stop();
                slideUpAnimation.play();
                mouseEvent.consume();
            });
            cardImage.setOnMouseExited(mouseEvent -> {
                slideUpAnimation.stop();
                slideDownAnimation.play();
                mouseEvent.consume();
            });
        }
        if (!opponent) {
            Animation slideUpAnimation = getSlideCardAnimation(cardImage, -80);
            cardImage.setOnMouseEntered(mouseEvent -> {
                slideDownAnimation.stop();
                slideUpAnimation.play();
                mouseEvent.consume();
            });
            cardImage.setOnMouseExited(mouseEvent -> {
                slideUpAnimation.stop();
                slideDownAnimation.play();
                mouseEvent.consume();
            });
            cardImage.setOnMouseClicked(mouseEvent -> {
                if (isOpponentTurn)
                    return;
                slideUpAnimation.stop();

                int columnIndex = GridPane.getColumnIndex(cardImage);

                controller.selectCard(Location.HAND, columnIndex + 1, false);

                try {
                    if (card instanceof Monster) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                            controller.summon();
                            attackerMonsterZone.add(
                                    createCardInMonsterZoneImage(card, false, false, false),
                                    selfMat.getCardCount(Location.MONSTER_ZONE) - 1, 0);
                            opponentMat.getDuelView().defenderMonsterZone.add(
                                    createCardInMonsterZoneImage(card, true, false, false),
                                    selfMat.getCardCount(Location.MONSTER_ZONE) - 1, 0);
                        } else {
                            controller.setMonster();
                            attackerMonsterZone.add(
                                    createCardInMonsterZoneImage(card, false, true, true),
                                    selfMat.getCardCount(Location.MONSTER_ZONE) - 1, 0);
                            opponentMat.getDuelView().defenderMonsterZone.add(
                                    createCardInMonsterZoneImage(card, true, true, true),
                                    selfMat.getCardCount(Location.MONSTER_ZONE) - 1, 0);
                        }
                    } else if (((SpellTrap) card).getType() == SpellTrapType.SPELL
                            && mouseEvent.getButton() == MouseButton.PRIMARY) {
                        if (((SpellTrap) card).getEffectType() == EffectType.FIELD) {
                            attackerFieldZone.setImage(new Image(card.getCardPicPath()));
                            opponentMat.getDuelView().defenderFieldZone.setImage(new Image(card.getCardPicPath()));
                            controller.activateSpell();
                        } else {
                            controller.setSpellOrTrap();

                            ImageView cardInSelfField = createCardInSpellZoneImage(card, false, false);
                            attackerSpellZone.add(cardInSelfField,
                                    selfMat.getCardCount(Location.SPELL_AND_TRAP_ZONE) - 1, 0);

                            ImageView cardInOpponentField = createCardInSpellZoneImage(card, true, false);
                            opponentMat.getDuelView().defenderSpellZone.add(cardInOpponentField,
                                    selfMat.getCardCount(Location.SPELL_AND_TRAP_ZONE) - 1, 0);

                            controller.activateSpell();

                            attackerSpellZone.getChildren().remove(cardInSelfField);
                            attackerGraveyard.setImage(new Image(card.getCardPicPath()));

                            opponentMat.getDuelView().defenderSpellZone.getChildren().remove(cardInOpponentField);
                            opponentMat.getDuelView().defenderGraveyard.setImage(new Image(card.getCardPicPath()));
                        }
                    } else {
                        controller.setSpellOrTrap();

                        attackerSpellZone.add(
                                createCardInSpellZoneImage(card, false, true),
                                selfMat.getCardCount(Location.SPELL_AND_TRAP_ZONE) - 1, 0);

                        opponentMat.getDuelView().defenderSpellZone.add(
                                createCardInSpellZoneImage(card, true, true),
                                selfMat.getCardCount(Location.SPELL_AND_TRAP_ZONE) - 1, 0);
                    }

                    attackerHand.getChildren().remove(columnIndex);
                    opponentMat.getDuelView().defenderHand.getChildren().remove(columnIndex);
                    refreshHandAfterRemove(columnIndex);

                    } catch (GameErrorException exception) {
                        new DialogPopUp(root, exception.getMessage()).initialize();
                    }
                controller.deselectCard();
            });
        }

        return cardImage;
    }

    public ImageView createCardInSpellZoneImage(Card card, boolean opponent, boolean hide) {
        ImageView cardImage = createCardImage(card, 55, opponent, hide);

        if (opponent)
            cardImage.setRotate(180.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }

    public ImageView createCardInMonsterZoneImage(Card card, boolean opponent, boolean hide, boolean horizontal) {
        ImageView cardImage = createCardImage(card, 55, opponent, hide);

        if (opponent)
            cardImage.setRotate(180.0);
        if (horizontal)
            cardImage.setRotate(90.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }

    public ImageView createCardInGraveyardImage(Card card, boolean opponent) {
        ImageView cardImage = createCardImage(card, 55, false, false);

        if (opponent)
            cardImage.setRotate(180.0);

        cardImage.setOnMouseClicked(mouseEvent -> {

        });

        return cardImage;
    }

    public void refreshHandAfterRemove(int columnIndex) {
        int columnCounter = columnIndex;
        int columnCount = attackerHand.getColumnCount();
        for (int i = 0; i < columnCount - columnIndex - 1; i++) {
            Node node = attackerHand.getChildren().get(columnIndex);
            attackerHand.getChildren().remove(node);

            attackerHand.add(node, columnCounter, 0);

            node = opponentMat.getDuelView().defenderHand.getChildren().get(columnIndex);
            opponentMat.getDuelView().defenderHand.getChildren().remove(node);

            opponentMat.getDuelView().defenderHand.add(node, columnCounter++, 0);
        }
    }
}