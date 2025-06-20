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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Swing 기반 게임 뷰
 */
public class SwingGameView implements GameView {
    private static final Logger logger = Logger.getLogger(SwingGameView.class.getName());
    private final Map<Horse, Point> horsePositions;
    private JFrame frame;
    private BoardPanel boardPanel;
    private JPanel playerPanel;
    private GameController controller;
    private Board board;
    private List<Player> players;

    public SwingGameView() {
        this.horsePositions = new HashMap<>();
        this.players = new ArrayList<>();
    }

    @Override
    public void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "UI 초기화 오류", e);
        }

        frame = new JFrame("윷놀이 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);
        frame.setLayout(new BorderLayout());

        boardPanel = new BoardPanel();
        boardPanel.setBackground(new Color(240, 240, 200));
        boardPanel.addMouseListener(new BoardClickListener());

        JScrollPane scrollPane = new JScrollPane(boardPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createTitledBorder("컨트롤"));

        JButton randomButton = new JButton("랜덤 윷 던지기");
        randomButton.addActionListener(e -> {
            if (controller != null) controller.handleYutThrow(true, null);
        });

        JComboBox<YutResult> specificCombo = new JComboBox<>(YutResult.values());
        JButton specificButton = new JButton("지정 윷 던지기");
        specificButton.addActionListener(e -> {
            if (controller != null) controller.handleYutThrow(false, (YutResult) specificCombo.getSelectedItem());
        });

        controlPanel.add(randomButton);
        controlPanel.add(specificCombo);
        controlPanel.add(specificButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

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
        if (board == null || players == null) return;

        for (Player player : players) {
            for (Horse horse : player.getHorses()) {
                if (horse.isFinished()) continue;
                Spot spot = horse.getCurrentSpot();
                if (spot != null) {
                    Point base = board.getSpotPosition(spot);
                    List<Horse> at = board.getHorsesAtSpot(spot);
                    int idx = at.indexOf(horse);
                    if (idx > 0) {
                        int ox = (idx % 2) * 10;
                        int oy = (idx / 2) * 10;
                        base = new Point(base.x + ox, base.y + oy);
                    }
                    horsePositions.put(horse, base);
                }
            }
        }
    }

    @Override
    public void updatePlayers(List<Player> players) {
        if (players == null) return;
        this.players = new ArrayList<>(players);

        playerPanel.removeAll();
        Game game = controller.getGame();
        for (Player p : players) {
            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBorder(BorderFactory.createLineBorder(p.getColor(), 2));
            info.setBackground(new Color(250, 250, 250));

            JLabel name = new JLabel(p.getName());
            name.setForeground(p.getColor());
            name.setFont(new Font("Arial", Font.BOLD, 14));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            info.add(name);

            JLabel status = new JLabel("말: " + p.getFinishedHorseCount() + "/" + p.getHorses().size());
            status.setAlignmentX(Component.CENTER_ALIGNMENT);
            info.add(status);

            if (p == game.getCurrentPlayer() && game.getState() == GameState.IN_PROGRESS && !game.getCurrentResults().isEmpty()) {
                JPanel buttons = new JPanel(new FlowLayout());
                buttons.setBackground(new Color(250, 250, 250));
                for (Horse h : p.getHorses()) {
                    if (!h.isFinished()) {
                        JButton b = new JButton("말 " + (h.getId() + 1));
                        b.addActionListener(e -> controller.handleHorseSelection(h));
                        buttons.add(b);
                    }
                }
                info.add(buttons);
            }
            playerPanel.add(info);
            playerPanel.add(Box.createVerticalStrut(10));
        }
        playerPanel.revalidate();
        playerPanel.repaint();
        updateHorsePositions();
        boardPanel.repaint();
    }

    private Game getGameFromController() {
        return controller.getGame();
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
        if (frame != null) frame.dispose();
    }

    @Override
    public YutResult promptYutSelection(List<YutResult> options) {
        JComboBox<YutResult> combo = new JComboBox<>(options.toArray(new YutResult[0]));
        int choice = JOptionPane.showConfirmDialog(frame, combo, "Select Yut Result", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return choice == JOptionPane.OK_OPTION ? (YutResult) combo.getSelectedItem() : null;
    }

    @Override
    public void showNotification(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }

    @Override
    public void onGameEvent(GameEvent event) {
        if (event == null) {
            return;
        }

        try {
            switch (event.getType()) {
                case GAME_SETUP: {
                    // 게임 설정 이벤트 처리
                    Object boardObj = event.get("board");
                    if (boardObj instanceof Board) {
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
                }

                case YUT_THROW: {
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
                }

                case HORSE_MOVE: {
                    // 말 이동 후 보드 업데이트
                    Game game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                        updatePlayers(game.getPlayers());
                    }
                    break;
                }

                case CAPTURE: {
                    // 말 잡기 후 보드 업데이트
                    JOptionPane.showMessageDialog(frame, "말을 잡았습니다! 한 번 더 던질 수 있습니다.");

                    Game game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                    }
                    break;
                }

                case GROUP: {
                    // 말 업기 후 보드 업데이트
                    Game game = getGameFromController();
                    if (game != null) {
                        updateBoard(game.getBoard());
                    }
                    break;
                }

                case TURN_CHANGE: {
                    // 턴 변경 시 플레이어 정보 업데이트
                    Player currentPlayer = (Player) event.get("player");
                    if (currentPlayer != null) {
                        JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "의 차례입니다.");
                    }

                    Game game = getGameFromController();
                    if (game != null) {
                        updatePlayers(game.getPlayers());
                    }
                    break;
                }

                case GAME_OVER: {
                    // 게임 종료 시 승자 표시
                    Player winner = (Player) event.get("winner");
                    showGameResult(winner);
                    break;
                }

                default:
                    System.out.println("처리되지 않은 이벤트 타입: " + event.getType());
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "예외 발생", e);
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

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
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            for (Line ln : board.getLines()) {
                Point a = board.getSpotPosition(ln.getFrom());
                Point b = board.getSpotPosition(ln.getTo());
                g2.drawLine(a.x, a.y, b.x, b.y);
            }
            for (Spot sp : board.getSpots()) {
                Point p = board.getSpotPosition(sp);
                int x = p.x - SPOT_SIZE / 2, y = p.y - SPOT_SIZE / 2;
                if (sp.isCorner()) {
                    g2.setColor(new Color(255, 200, 100));
                    g2.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else if (sp.isStart()) {
                    g2.setColor(new Color(100, 200, 100));
                    g2.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else if (sp.isFinish()) {
                    g2.setColor(new Color(100, 100, 255));
                    g2.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillOval(x, y, SPOT_SIZE, SPOT_SIZE);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(x, y, SPOT_SIZE, SPOT_SIZE);
                }
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(sp.getId()), p.x - 5, p.y + 5);
            }
            for (Map.Entry<Horse, Point> e : horsePositions.entrySet()) {
                Horse h = e.getKey();
                Point p = e.getValue();
                int size = SPOT_SIZE - 6;
                int x = p.x - size / 2, y = p.y - size / 2;
                g2.setColor(h.getOwner().getColor());
                g2.fillRoundRect(x, y, size, size, 8, 8);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, y, size, size, 8, 8);
                g2.setColor(Color.WHITE);
                g2.drawString(String.valueOf(h.getId() + 1), p.x - 3, p.y + 4);
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
