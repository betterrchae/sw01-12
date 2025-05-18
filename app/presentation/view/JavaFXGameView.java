// JavaFXGameView.java
package app.presentation.view;

import app.Controller.GameController;
import app.Model.Board;
import app.Model.Enum.BoardType;
import app.Model.Enum.GameState;
import app.Model.Enum.YutResult;
import app.Model.Event.GameEvent;
import app.Model.Game;
import app.Model.Horse.Horse;
import app.Model.Line;
import app.Model.Player.Player;
import app.Model.Spot;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaFXGameView implements GameView {
    private static final Logger logger = Logger.getLogger(JavaFXGameView.class.getName());
    private GameController controller;
    private Board board;
    private List<Player> players = new ArrayList<>();
    private final Map<Horse, Point2D> horsePositions = new HashMap<>();

    private final Stage stage;
    private BorderPane root;
    private Canvas canvas;
    private VBox playerPanel;
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 600;
    private final int SPOT_SIZE = 30;

    public JavaFXGameView(Stage Stage) {
        this.stage = Stage;
        Platform.runLater(stage::show);
    }

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            root = new BorderPane();
            Scene scene = new Scene(root, 1200, 1000);
            stage.setTitle("윷놀이 게임");
            stage.setScene(scene);

            canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            canvas.setOnMouseClicked(this::handleCanvasClick);
            ScrollPane scrollPane = new ScrollPane(canvas);
            root.setCenter(scrollPane);

            HBox controlPanel = new HBox(10);
            controlPanel.setPadding(new Insets(10));
            controlPanel.setAlignment(Pos.CENTER);

            Button randomButton = new Button("랜덤 윷 던지기");
            randomButton.setOnAction(e -> controller.handleYutThrow(true, null));

            ComboBox<YutResult> comboBox = new ComboBox<>(FXCollections.observableArrayList(YutResult.values()));
            comboBox.getSelectionModel().selectFirst();

            Button specificButton = new Button("지정 윷 던지기");
            specificButton.setOnAction(e -> controller.handleYutThrow(false, comboBox.getValue()));

            controlPanel.getChildren().addAll(randomButton, comboBox, specificButton);
            root.setBottom(controlPanel);

            playerPanel = new VBox(10);
            playerPanel.setPadding(new Insets(10));
            playerPanel.setPrefWidth(200);
            root.setRight(playerPanel);

            stage.setOnCloseRequest(e -> {
                e.consume();
                Platform.exit();
            });

            stage.centerOnScreen();
            stage.show();
            stage.requestFocus();
            stage.toFront();
        });
    }

    private void updateHorsePositions() {
        horsePositions.clear();
        if (board == null || players == null) return;

        for (Player player : players) {
            for (Horse horse : player.getHorses()) {
                if (horse.isFinished()) continue;
                Spot spot = horse.getCurrentSpot();
                if (spot != null) {
                    Point2D base = toPoint2D(board.getSpotPosition(spot));
                    List<Horse> at = board.getHorsesAtSpot(spot);
                    int idx = at.indexOf(horse);
                    if (idx > 0) {
                        double ox = (idx % 2) * 10;
                        double oy = ((double) idx / 2) * 10;
                        base = base.add(ox, oy);
                    }
                    horsePositions.put(horse, base);
                }
            }
        }
    }

    @Override
    public void updateBoard(Board board) {
        this.board = board;
        adjustCanvasSize();
        updateHorsePositions();
        redrawBoard();
    }

    private void redrawBoard() {
        Platform.runLater(() -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            if (board == null) return;
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            for (Line line : board.getLines()) {
                Point2D from = toPoint2D(board.getSpotPosition(line.getFrom()));
                Point2D to = toPoint2D(board.getSpotPosition(line.getTo()));
                gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
            }

            for (Spot spot : board.getSpots()) {
                Point2D p = toPoint2D(board.getSpotPosition(spot));
                double x = p.getX() - (double) SPOT_SIZE / 2;
                double y = p.getY() - (double) SPOT_SIZE / 2;

                if (spot.isCorner()) gc.setFill(Color.ORANGE);
                else if (spot.isStart()) gc.setFill(Color.GREEN);
                else if (spot.isFinish()) gc.setFill(Color.BLUE);
                else gc.setFill(Color.WHITE);

                gc.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeOval(x, y, SPOT_SIZE, SPOT_SIZE);
                gc.strokeText(String.valueOf(spot.getId()), p.getX() - 5, p.getY() + 5);
            }

            for (Map.Entry<Horse, Point2D> e : horsePositions.entrySet()) {
                Horse h = e.getKey();
                Point2D p = e.getValue();
                double size = SPOT_SIZE - 6;
                double x = p.getX() - size / 2;
                double y = p.getY() - size / 2;

                gc.setFill(toFXColor(h.getOwner().getColor()));
                gc.fillRoundRect(x, y, size, size, 8, 8);
                gc.setStroke(Color.BLACK);
                gc.strokeRoundRect(x, y, size, size, 8, 8);
                gc.setFill(Color.WHITE);
                gc.setFont(new Font(12));
                gc.fillText(String.valueOf(h.getId() + 1), p.getX() - 3, p.getY() + 4);
            }
        });
    }

    @Override
    public void updatePlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
        Platform.runLater(() -> {
            playerPanel.getChildren().clear();
            Game game = controller.getGame();
            for (Player p : players) {
                VBox playerBox = new VBox(5);
                playerBox.setPadding(new Insets(5));
                playerBox.setStyle("-fx-border-color: " + toHex(p.getColor()) + "; -fx-background-color: #FAFAFA;");

                Label nameLabel = new Label(p.getName());
                nameLabel.setTextFill(toFXColor(p.getColor()));
                nameLabel.setFont(Font.font("Arial", 14));
                playerBox.getChildren().add(nameLabel);

                Label status = new Label("말: " + p.getFinishedHorseCount() + "/" + p.getHorses().size());
                playerBox.getChildren().add(status);

                if (p == game.getCurrentPlayer() && game.getState() == GameState.IN_PROGRESS && !game.getCurrentResults().isEmpty()) {
                    HBox buttons = new HBox(5);
                    for (Horse h : p.getHorses()) {
                        if (!h.isFinished()) {
                            Button b = new Button("말 " + (h.getId() + 1));
                            b.setOnAction(e -> controller.handleHorseSelection(h));
                            buttons.getChildren().add(b);
                        }
                    }
                    playerBox.getChildren().add(buttons);
                }
                playerPanel.getChildren().add(playerBox);
            }
            updateHorsePositions();
            redrawBoard();
        });
    }

    private void handleCanvasClick(MouseEvent e) {
        Game game = controller.getGame();
        if (game == null || game.getState() != GameState.IN_PROGRESS || game.getCurrentResults().isEmpty()) return;

        for (Map.Entry<Horse, Point2D> entry : horsePositions.entrySet()) {
            Horse horse = entry.getKey();
            Point2D pos = entry.getValue();
            double size = SPOT_SIZE - 6;
            if (e.getX() >= pos.getX() - size / 2 && e.getX() <= pos.getX() + size / 2 &&
                    e.getY() >= pos.getY() - size / 2 && e.getY() <= pos.getY() + size / 2) {
                if (horse.getOwner() == game.getCurrentPlayer()) {
                    controller.handleHorseSelection(horse);
                    return;
                }
            }
        }
    }

    @Override
    public void showYutResult(YutResult result) {
        Platform.runLater(() -> {
            String playerName = Optional.ofNullable(controller.getGame().getCurrentPlayer()).map(Player::getName).orElse("현재 플레이어");
            new Alert(Alert.AlertType.INFORMATION, playerName + "의 윷 결과: " + result.getDisplayName()).showAndWait();
        });
    }

    @Override
    public void showGameResult(Player winner) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("게임 종료");
            alert.setHeaderText(null);
            alert.setContentText(winner.getName() + "님이 승리했습니다! 새 게임을 시작하시겠습니까?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) controller.handleRestartGame();
            else controller.handleExitGame();
        });
    }

    @Override
    public void showSetupDialog() {
        Platform.runLater(() -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("게임 설정");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20));

            Spinner<Integer> playerSpinner = new Spinner<>(2, 4, 2);
            Spinner<Integer> horseSpinner = new Spinner<>(2, 5, 2);
            ComboBox<BoardType> boardCombo = new ComboBox<>(FXCollections.observableArrayList(BoardType.values()));
            boardCombo.getSelectionModel().selectFirst();

            grid.addRow(0, new Label("참가자 수 (2-4):"), playerSpinner);
            grid.addRow(1, new Label("말 개수 (2-5):"), horseSpinner);
            grid.addRow(2, new Label("보드 유형:"), boardCombo);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(type -> {
                if (type == ButtonType.OK) {
                    controller.setupGame(playerSpinner.getValue(), horseSpinner.getValue(), boardCombo.getValue());
                } else {
                    Platform.exit();
                }
            });
        });
    }

    @Override
    public void close() {
        if (stage != null) {
            Platform.runLater(stage::close);
        }
    }

    @Override
    public YutResult promptYutSelection(List<YutResult> options) {
        ChoiceDialog<YutResult> dialog = new ChoiceDialog<>(options.getFirst(), options);
        dialog.setTitle("윷 선택");
        dialog.setContentText("결과를 선택하세요:");
        Optional<YutResult> result = dialog.showAndWait();
        return result.orElse(null);
    }

    @Override
    public void showNotification(String msg) {
        Runnable showAlert = () -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("알림");
            alert.setHeaderText(null);

            Label label = new Label(msg);
            label.setWrapText(true);
            label.setMaxWidth(Double.MAX_VALUE);

            alert.getDialogPane().setContent(label);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

            alert.showAndWait();
        };

        if (Platform.isFxApplicationThread()) {
            showAlert.run();
        } else {
            Platform.runLater(showAlert);
        }
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if (event == null) return;
        try {
            switch (event.getType()) {
                case GAME_SETUP:
                    Object boardObj = event.get("board");
                    updateBoard((boardObj instanceof Board) ? (Board) boardObj : controller.getGame().getBoard());
                    List<Player> setupPlayers = (List<Player>) event.get("players");
                    updatePlayers(setupPlayers != null ? setupPlayers : controller.getGame().getPlayers());
                    break;

                case YUT_THROW:
                    YutResult result = (YutResult) event.get("result");
                    String reason = (String) event.get("reason");
                    if (reason != null && result == YutResult.BACKDO) {
                        showNotification(reason);
                    } else {
                        showYutResult(result);
                        updatePlayers(controller.getGame().getPlayers());
                    }
                    break;

                case HORSE_MOVE:
                    updateBoard(controller.getGame().getBoard());
                    updatePlayers(controller.getGame().getPlayers());
                    break;

                case CAPTURE:
                    showNotification("말을 잡았습니다! 한 번 더 던질 수 있습니다.");
                    updateBoard(controller.getGame().getBoard());
                    break;

                case GROUP:
                    updateBoard(controller.getGame().getBoard());
                    break;

                case TURN_CHANGE:
                    Player currentPlayer = (Player) event.get("player");
                    if (currentPlayer != null) {
                        showNotification(currentPlayer.getName() + "의 차례입니다.");
                    }
                    updatePlayers(controller.getGame().getPlayers());
                    break;

                case GAME_OVER:
                    Player winner = (Player) event.get("winner");
                    showGameResult(winner);
                    break;

                default:
                    System.out.println("처리되지 않은 이벤트 타입: " + event.getType());
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "게임 이벤트 처리 중 예외 발생", e);
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private Point2D toPoint2D(java.awt.Point p) {
        return new Point2D(p.x, p.y);
    }

    private Color toFXColor(java.awt.Color awtColor) {
        return Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    private String toHex(java.awt.Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void adjustCanvasSize() {
        if (board == null) return;

        int maxX = 0;
        int maxY = 0;
        for (Spot spot : board.getSpots()) {
            java.awt.Point pt = board.getSpotPosition(spot);
            if (pt.x > maxX) maxX = pt.x;
            if (pt.y > maxY) maxY = pt.y;
        }

        canvas.setWidth(maxX + 100);
        canvas.setHeight(maxY + 100);
    }
}

