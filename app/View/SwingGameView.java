package app.View;

import javax.swing.*;

import app.Controller.GameController;
import app.Model.Enum.BoardType;
import app.Model.Enum.GameState;
import app.Model.Enum.YutResult;
import app.Model.Event.GameEvent;
import app.Model.Board;
import app.Model.Game;
import app.Model.Horse.Horse;
import app.Model.Line;
import app.Model.Player.Player;
import app.Model.Spot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class SwingGameView implements GameView {
    private JFrame frame;
    private BoardPanel boardPanel;
    private JPanel controlPanel;
    private JPanel playerPanel;
    private GameController controller;
    private Board board;
    private List<Player> players;
    private Map<Horse, Point> horsePositions;

    public SwingGameView() {
        this.horsePositions = new HashMap<>();
        this.players = new ArrayList<>();
    }

    @Override
    public void initialize() {
        // Swing UI 초기화
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("윷놀이 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        // 보드 패널
        boardPanel = new BoardPanel();
        boardPanel.setBackground(new Color(240, 240, 200));
        boardPanel.addMouseListener(new BoardClickListener());

        JScrollPane scrollPane = new JScrollPane(boardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 컨트롤 패널
        controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createTitledBorder("컨트롤"));

        JButton randomButton = new JButton("랜덤 윷 던지기");
        randomButton.addActionListener(e -> {
            if (controller != null) {
                controller.handleYutThrow(true, null);
            }
        });

        JComboBox<YutResult> specificYutCombo = new JComboBox<>(YutResult.values());
        JButton specificButton = new JButton("지정 윷 던지기");
        specificButton.addActionListener(e -> {
            if (controller != null) {
                controller.handleYutThrow(false, (YutResult) specificYutCombo.getSelectedItem());
            }
        });

        controlPanel.add(randomButton);
        controlPanel.add(specificYutCombo);
        controlPanel.add(specificButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        // 플레이어 패널
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("플레이어"));
        playerPanel.setPreferredSize(new Dimension(200, 0));
        frame.add(playerPanel, BorderLayout.EAST);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void updateBoard(Board board) {
        this.board = board;
        if (board != null) {
            updateHorsePositions();
            boardPanel.repaint();
        }
    }

    private void updateHorsePositions() {
        horsePositions.clear();

        if (board == null || players == null) {
            return;
        }

        // 각 플레이어의 말 위치 업데이트
        for (Player player : players) {
            for (Horse horse : player.getHorses()) {
                if (horse.isFinished()) {
                    continue;
                }
                Spot spot = horse.getCurrentSpot();
                if (spot != null) {
                    Point basePosition = spot.getPosition();

                    // 같은 칸에 여러 말이 있을 경우 약간 위치 조정
                    List<Horse> horsesAtSpot = board.getHorsesAtSpot(spot);
                    int index = horsesAtSpot.indexOf(horse);

                    if (index > 0) {
                        // 말을 약간 어긋나게 배치
                        int offsetX = (index % 2) * 10;
                        int offsetY = (index / 2) * 10;
                        basePosition = new Point(basePosition.x + offsetX, basePosition.y + offsetY);
                    }

                    horsePositions.put(horse, basePosition);
                }
            }
        }
    }

    @Override
    public void updatePlayers(List<Player> players) {
        if (players == null) {
            System.err.println("Warning: updatePlayers called with null players list");
            return;
        }

        this.players = new ArrayList<>(players);

        // 플레이어 UI 업데이트
        playerPanel.removeAll();

        for (Player player : players) {
            JPanel playerInfo = new JPanel();
            playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
            playerInfo.setBorder(BorderFactory.createLineBorder(player.getColor(), 2));
            playerInfo.setBackground(new Color(250, 250, 250));

            JLabel nameLabel = new JLabel(player.getName());
            nameLabel.setForeground(player.getColor());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerInfo.add(nameLabel);

            JLabel horseLabel = new JLabel("말: " + player.getFinishedHorseCount() + "/" + player.getHorses().size());
            horseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerInfo.add(horseLabel);

            // 말 선택 버튼 추가
            Game game = getGameFromController();
            if (game != null && player == game.getCurrentPlayer() && game.getState() == GameState.IN_PROGRESS && !game.getCurrentResults().isEmpty()) {

                JPanel horseButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
                horseButtons.setBackground(new Color(250, 250, 250));

                for (Horse horse : player.getHorses()) {
                    if (!horse.isFinished()) {
                        JButton horseButton = new JButton("말 " + (horse.getId() + 1));
                        horseButton.addActionListener(e -> {
                            if (controller != null) {
                                controller.handleHorseSelection(horse);
                            }
                        });
                        horseButtons.add(horseButton);
                    }
                }

                playerInfo.add(horseButtons);
            }

            playerPanel.add(playerInfo);
            playerPanel.add(Box.createVerticalStrut(10));
        }

        playerPanel.revalidate();
        playerPanel.repaint();

        // 말 위치 업데이트 및 보드 다시 그리기
        updateHorsePositions();
        boardPanel.repaint();
    }

    private Game getGameFromController() {
        return (controller != null) ? controller.getGame() : null;
    }

    @Override
    public void showYutResult(YutResult result) {
        if (result == null) {
            return;
        }

        // 윷 결과 표시
        Game game = getGameFromController();
        String playerName = (game != null && game.getCurrentPlayer() != null) ? game.getCurrentPlayer().getName() : "현재 플레이어";

        JOptionPane.showMessageDialog(frame, playerName + "의 윷 결과: " + result.getDisplayName(), "윷 결과", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showGameResult(Player winner) {
        if (winner == null) {
            return;
        }

        // 게임 결과 표시
        int option = JOptionPane.showConfirmDialog(frame, winner.getName() + "님이 승리했습니다!\n새 게임을 시작하시겠습니까?", "게임 종료", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (controller != null) {
                controller.handleRestartGame();
            }
        } else {
            if (controller != null) {
                controller.handleExitGame();
            }
        }
    }

    @Override
    public void showSetupDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel playerLabel = new JLabel("참가자 수 (2-4):");
        JSpinner playerSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));

        JLabel horseLabel = new JLabel("말 개수 (2-5):");
        JSpinner horseSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 5, 1));

        JLabel boardLabel = new JLabel("보드 유형:");
        JComboBox<BoardType> boardCombo = new JComboBox<>(BoardType.values());

        panel.add(playerLabel);
        panel.add(playerSpinner);
        panel.add(horseLabel);
        panel.add(horseSpinner);
        panel.add(boardLabel);
        panel.add(boardCombo);

        int result = JOptionPane.showConfirmDialog(frame, panel, "게임 설정", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int playerCount = (Integer) playerSpinner.getValue();
            int horseCount = (Integer) horseSpinner.getValue();
            BoardType boardType = (BoardType) boardCombo.getSelectedItem();

            if (controller != null) {
                controller.setupGame(playerCount, horseCount, boardType);
            }
        } else {
            // 설정 취소 시 프로그램 종료
            System.exit(0);
        }
    }

    @Override
    public void close() {
        if (frame != null) {
            frame.dispose();
        }
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if (event == null) {
            return;
        }

        try {
            switch (event.getType()) {
                case GAME_SETUP:
                    // 게임 설정 이벤트 처리
                    Object boardObj = event.get("board");
                    if (boardObj != null && boardObj instanceof Board) {
                        updateBoard((Board) boardObj);
                    } else {
                        // Game 객체에서 보드 직접 가져오기
                        Game game = getGameFromController();
                        if (game != null) {
                            updateBoard(game.getBoard());
                        }
                    }

                    @SuppressWarnings("unchecked") List<Player> setupPlayers = (List<Player>) event.get("players");
                    if (setupPlayers != null) {
                        updatePlayers(setupPlayers);
                    } else {
                        // Game 객체에서 플레이어 직접 가져오기
                        Game game = getGameFromController();
                        if (game != null) {
                            updatePlayers(game.getPlayers());
                        }
                    }
                    break;

                case YUT_THROW:
                    YutResult result = (YutResult) event.get("result");

                    // 빽도 특별 메시지 처리
                    String reason = (String) event.get("reason");
                    if (reason != null && result == YutResult.BACKDO) {
                        // 빽도로 인한 턴 변경 메시지
                        JOptionPane.showMessageDialog(frame, reason, "빽도 - 턴 변경", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 일반 윷 결과 표시
                        showYutResult(result);

                        // 윷 던진 후 플레이어 정보 업데이트 (사용 가능한 말 표시)
                        Game game = getGameFromController();
                        if (game != null) {
                            updatePlayers(game.getPlayers());
                        }
                    }
                    break;

                case HORSE_MOVE:
                    // 말 이동 후 보드 업데이트
                    Game game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                        updatePlayers(game.getPlayers());
                    }
                    break;

                case CAPTURE:
                    // 말 잡기 후 보드 업데이트
                    JOptionPane.showMessageDialog(frame, "말을 잡았습니다! 한 번 더 던질 수 있습니다.");

                    game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                    }
                    break;

                case GROUP:
                    // 말 업기 후 보드 업데이트
                    game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                    }
                    break;

                case TURN_CHANGE:
                    // 턴 변경 시 플레이어 정보 업데이트
                    Player currentPlayer = (Player) event.get("player");
                    if (currentPlayer != null) {
                        JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "의 차례입니다.");
                    }

                    game = getGameFromController();
                    if (game != null) {
                        updatePlayers(game.getPlayers());
                    }
                    break;

                case GAME_OVER:
                    // 게임 종료 시 승자 표시
                    Player winner = (Player) event.get("winner");
                    showGameResult(winner);
                    break;

                default:
                    System.out.println("처리되지 않은 이벤트 타입: " + event.getType());
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error in event handling: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * 보드 클릭 이벤트 처리
     */
    private class BoardClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            Game game = getGameFromController();
            if (game == null || game.getState() != GameState.IN_PROGRESS || game.getCurrentResults().isEmpty()) {
                return;
            }

            // 클릭한 위치에 말이 있는지 확인
            Point clickPoint = e.getPoint();
            for (Map.Entry<Horse, Point> entry : horsePositions.entrySet()) {
                Horse horse = entry.getKey();
                Point horsePos = entry.getValue();

                // 말이 클릭 영역 내에 있는지 확인
                int horseSize = BoardPanel.SPOT_SIZE - 6;
                Rectangle horseBounds = new Rectangle(horsePos.x - horseSize / 2, horsePos.y - horseSize / 2, horseSize, horseSize);

                if (horseBounds.contains(clickPoint)) {
                    if (horse.getOwner() == game.getCurrentPlayer()) {
                        controller.handleHorseSelection(horse);
                        return;
                    }
                }
            }
        }
    }

    /**
     * 윷놀이 보드를 그리는 커스텀 패널
     */
    private class BoardPanel extends JPanel {
        public static final int SPOT_SIZE = 30;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (board == null) {
                g.setColor(Color.RED);
                g.drawString("보드가 초기화되지 않았습니다.", 50, 50);
                return;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 선 그리기
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));

            System.out.println(board.getLines());
            for (Line line : board.getLines()) {
                Point fromPos = line.getFrom().getPosition();
                Point toPos = line.getTo().getPosition();
                g2d.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
            }

            // 칸 그리기
            for (Spot spot : board.getSpots()) {
                Point pos = spot.getPosition();
                int x = pos.x - SPOT_SIZE / 2;
                int y = pos.y - SPOT_SIZE / 2;

                if (spot.isCorner()) {
                    // 모서리 칸은 다른 색상으로
                    g2d.setColor(new Color(255, 200, 100));
                    g2d.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else if (spot.isStart()) {
                    // 시작 칸
                    g2d.setColor(new Color(100, 200, 100));
                    g2d.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else if (spot.isFinish()) {
                    // 도착 칸
                    g2d.setColor(new Color(100, 100, 255));
                    g2d.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else {
                    // 일반 칸
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                }

                // 칸 번호 표시
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf(spot.getId()), pos.x - 5, pos.y + 5);
            }

            // 말 그리기
            for (Map.Entry<Horse, Point> entry : horsePositions.entrySet()) {
                Horse horse = entry.getKey();
                Point pos = entry.getValue();

                // 말 크기는 칸보다 약간 작게
                int horseSize = SPOT_SIZE - 6;
                int x = pos.x - horseSize / 2;
                int y = pos.y - horseSize / 2;

                // 말 소유자의 색상으로 그리기
                g2d.setColor(horse.getOwner().getColor());
                g2d.fillRoundRect(x, y, horseSize, horseSize, 8, 8);
                g2d.setColor(Color.BLACK);
                g2d.drawRoundRect(x, y, horseSize, horseSize, 8, 8);

                g2d.setColor(Color.WHITE);
                g2d.drawString(String.valueOf(horse.getId() + 1), pos.x - 3, pos.y + 4);

            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(800, 600);
        }

    }
}