package view;

import controller.ScoreboardController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ScoreboardView extends View{

    private final ScoreboardController controller = new ScoreboardController();

    @FXML
    private VBox root;
    @FXML
    private TableView<ScoreboardData> scoreboard;

    public void initialize() {
        scoreboard.setRowFactory(tableView -> new TableRow<>());


        TableColumn<ScoreboardData, ?> rankColumn = column("Rank", ScoreboardData::getRankProperty);
        TableColumn<ScoreboardData, ?> nicknameColumn = column("Name", ScoreboardData::getNicknameProperty);
        TableColumn<ScoreboardData, ?> scoreColumn = column("Score", ScoreboardData::getScoreProperty);

        scoreboard.getColumns().addAll(rankColumn, nicknameColumn, scoreColumn);

        scoreboard.getColumns().forEach(column -> {
            column.setResizable(false);
            column.setReorderable(false);
        });

        List<ScoreboardData> scoreboardData = new ArrayList<>();
        int counter = 0;
        int rank = 1;
        for (int i = 0; i < controller.getScoreboard().size(); i++) {
            if (counter >= 20) break;
            for (int j = 0; j < controller.getScoreboard().get(i).size(); j++) {
                scoreboardData.add(new ScoreboardData(rank, controller.getScoreboard().get(i).get(j).getNickname(),
                        controller.getScoreboard().get(i).get(j).getScore()));
                counter++;
            }
            rank++;
        }

        scoreboard.setItems(FXCollections.observableList(scoreboardData));
        scoreboard.refresh();
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        enterNewMenu("/fxml/mainmenu.fxml", root);
    }

    private static <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return column;
    }

    public static class ScoreboardData {

        private final IntegerProperty rank;
        private final StringProperty nickname;
        private final IntegerProperty score;

        private ScoreboardData(int rank, String nickname, int score) {
            this.rank = new SimpleIntegerProperty(rank);
            this.nickname = new SimpleStringProperty(nickname);
            this.score = new SimpleIntegerProperty(score);
        }

        public int getRank() {
            return rank.get();
        }

        public IntegerProperty getRankProperty() {
            return rank;
        }

        public String getNickname() {
            return nickname.get();
        }

        public StringProperty getNicknameProperty() {
            return nickname;
        }

        public int getScore() {
            return score.get();
        }

        public IntegerProperty getScoreProperty() {
            return score;
        }
    }
}
