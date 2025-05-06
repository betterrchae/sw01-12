package app.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import app.Model.Enum.BoardType;
import app.Model.Enum.GameEventType;
import app.Model.Enum.GameState;
import app.Model.Enum.YutResult;
import app.Model.Event.GameEvent;
import app.Model.Event.GameEventListener;
import app.Model.Event.GameEventManager;
import app.Model.Horse.Horse;
import app.Model.Horse.HorseGroup;
import app.Model.Player.Player;
import app.Model.Strategy.RandomYutThrowStrategy;
import app.Model.Strategy.SpecificYutThrowStrategy;
import app.View.BoardFactory;

import java.awt.*;

/**
 * 게임 클래스
 */
public class Game {
    private final List<Player> players;
    private Board board;
    private final Yut yut;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private GameState state;
    private final GameEventManager eventManager;
    private List<YutResult> currentResults;
    private boolean canThrowAgain;

    public Game() {
        this.players = new ArrayList<>();
        this.board = null;
        this.yut = new Yut();
        this.state = GameState.SETUP;
        this.eventManager = new GameEventManager();
        this.currentResults = new ArrayList<>();
        this.canThrowAgain = false;
    }

    public void setupGame(int playerCount, int horseCount, BoardType boardType) {
        if (state != GameState.SETUP) {
            return;
        }

        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("Player count must be between 2 and 4");
        }

        if (horseCount < 2 || horseCount > 5) {
            throw new IllegalArgumentException("Horse count must be between 2 and 5");
        }

        // 플레이어 초기화
        players.clear();
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1), colors[i], horseCount));
        }

        // 보드 초기화
        this.board = BoardFactory.createBoard(boardType);

        // 게임 상태 초기화
        currentPlayerIndex = 0;
        currentPlayer = players.get(0);
        state = GameState.IN_PROGRESS;
        currentResults.clear();
        canThrowAgain = false;

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("playerCount", playerCount);
        data.put("horseCount", horseCount);
        data.put("boardType", boardType);
        data.put("players", Collections.unmodifiableList(players));
        data.put("board", board);
        eventManager.fireEvent(new GameEvent(GameEventType.GAME_SETUP, data));
    }

    public YutResult throwYut(boolean isRandom, YutResult specificResult) {
        if (state != GameState.IN_PROGRESS) {
            return null;
        }

        // 윷 던지기 전략 설정
        if (isRandom) {
            yut.setStrategy(new RandomYutThrowStrategy());
        } else {
            yut.setStrategy(new SpecificYutThrowStrategy(specificResult));
        }

        // 윷 던지기
        YutResult result = yut.throwYut();

        // 빽도가 나왔고 현재 플레이어의 모든 말이 아직 시작하지 않았으면 턴 넘김
        if (result == YutResult.BACKDO && !hasMovableHorses(result)) {
            // 이벤트 발생: 빽도로 인한 턴 변경
            Map<String, Object> data = new HashMap<>();
            data.put("player", currentPlayer);
            data.put("result", result);
            data.put("reason", "말이 없거나 모든 말이 시작 전 상태에서 빽도가 나와 턴을 넘깁니다.");
            eventManager.fireEvent(new GameEvent(GameEventType.YUT_THROW, data));

            // 턴 넘김
            nextTurn();
            return result;
        }
        // 현재 결과에 추가
        currentResults.add(result);

        // 윷이나 모가 나오면 한 번 더 던질 수 있음
        canThrowAgain = result.canThrowAgain();

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("player", currentPlayer);
        data.put("result", result);
        data.put("canThrowAgain", canThrowAgain);
        eventManager.fireEvent(new GameEvent(GameEventType.YUT_THROW, data));

        return result;
    }

    /**
     * 현재 플레이어가 주어진 윷 결과로 이동 가능한 말이 있는지 확인
     */
    private boolean hasMovableHorses(YutResult result) {
        // 빽도가 아니면 항상 이동 가능 (출발 전 말로 이동 가능)
        if (result != YutResult.BACKDO) {
            return true;
        }

        // 빽도인 경우, 이미 보드 위에 있는 말이 있는지 확인
        for (Horse horse : currentPlayer.getHorses()) {
            // 말이 보드 위에 있고, 시작 지점이 아니면 이동 가능
            if (horse.getCurrentSpot() != null && !horse.getCurrentSpot().isStart() && !horse.isFinished()) {
                return true;
            }
        }

        // 이동 가능한 말이 없음
        return false;
    }

    public boolean moveHorse(Horse horse, YutResult result) {
        if (state != GameState.IN_PROGRESS || !currentResults.contains(result)) {
            return false;
        }

        // 말이 현재 플레이어의 것인지 확인
        if (horse.getOwner() != currentPlayer) {
            return false;
        }

        // 말이 이미 완주했는지 확인
        if (horse.isFinished()) {
            return false;
        }

        Spot currentSpot = horse.getCurrentSpot();
        Spot nextSpot = board.calculateNextSpot(currentSpot, result);

        if (nextSpot == null) {
            return false;
        }

        // 말 또는 말 그룹 이동
        boolean moved = false;
        if (horse.isInGroup()) {
            HorseGroup group = horse.getGroup();
            moved = group.move(nextSpot);
        } else {
            moved = horse.move(nextSpot);
            board.updateHorsePosition(horse);
        }

        if (!moved) {
            return false;
        }

        // 현재 결과에서 사용한 결과 제거
        currentResults.remove(result);

        // 도착 칸에 다른 플레이어의 말이 있는지 확인
        List<Horse> horsesAtSpot = board.getHorsesAtSpot(nextSpot);
        List<Horse> enemyHorses = horsesAtSpot.stream()
                .filter(h -> h.getOwner() != currentPlayer && !nextSpot.isFinish())
                .collect(Collectors.toList());

        boolean captured = false;
        if (!enemyHorses.isEmpty()) {
            // 상대 말 잡기
            captured = captureHorses(horse, enemyHorses);
        } else {
            // 같은 플레이어의 말이 있는지 확인해 업기
            List<Horse> friendlyHorses = horsesAtSpot.stream()
                    .filter(h -> h.getOwner() == currentPlayer && h != horse)
                    .collect(Collectors.toList());

            if (!friendlyHorses.isEmpty()) {
                groupHorses(horse, friendlyHorses);
            }
        }

        // 도착 지점에 도달했는지 확인
        if (nextSpot.isFinish()) {
            if (horse.isInGroup()) {
                for (Horse h : horse.getGroup().getHorses()) {
                    h.setFinished(true);
                }
            } else {
                horse.setFinished(true);
            }
        }

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("player", currentPlayer);
        data.put("horse", horse);
        data.put("from", currentSpot);
        data.put("to", nextSpot);
        data.put("result", result);
        eventManager.fireEvent(new GameEvent(GameEventType.HORSE_MOVE, data));

        // 모든 말이 완주했는지 확인
        if (currentPlayer.isAllHorsesFinished()) {
            endGame();
            return true;
        }

        // 윷을 다 사용했거나 잡기가 없으면 턴 종료
        if (currentResults.isEmpty() && !captured && !canThrowAgain) {
            nextTurn();
        }

        return true;
    }

    private boolean captureHorses(Horse attacker, List<Horse> enemyHorses) {
        if (enemyHorses.isEmpty()) {
            return false;
        }

        // 잡힌 말들을 시작 위치로 되돌림
        for (Horse enemy : enemyHorses) {
            if (enemy.isInGroup()) {
                HorseGroup group = enemy.getGroup();
                for (Horse h : group.getHorses()) {
                    h.setCurrentSpot(null);
                    board.updateHorsePosition(h);
                }
                // 그룹 해제
                for (Horse h : new ArrayList<>(group.getHorses())) {
                    group.removeHorse(h);
                }
            } else {
                enemy.setCurrentSpot(null);
                board.updateHorsePosition(enemy);
            }
        }

        // 잡으면 한 번 더 던질 수 있음
        canThrowAgain = true;

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("attacker", attacker);
        data.put("captured", enemyHorses);
        eventManager.fireEvent(new GameEvent(GameEventType.CAPTURE, data));

        return true;
    }

    private void groupHorses(Horse horse, List<Horse> friendlyHorses) {
        if (friendlyHorses.isEmpty()) {
            return;
        }

        // 이미 그룹이 있는지 확인
        HorseGroup group = null;

        // 친구 말 중 그룹이 있는지 확인
        for (Horse friend : friendlyHorses) {
            if (friend.isInGroup()) {
                group = friend.getGroup();
                break;
            }
        }

        // 현재 말이 그룹에 있는지 확인
        if (horse.isInGroup()) {
            group = horse.getGroup();
        }

        // 그룹이 없으면 새로 생성
        if (group == null) {
            group = new HorseGroup(horse);
        } else if (!horse.isInGroup()) {
            // 말이 그룹에 없으면 추가
            group.addHorse(horse);
        }

        // 나머지 말들도 그룹에 추가
        for (Horse friend : friendlyHorses) {
            if (!friend.isInGroup()) {
                group.addHorse(friend);
            }
        }

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("group", group);
        eventManager.fireEvent(new GameEvent(GameEventType.GROUP, data));
    }

    private void nextTurn() {
        // 다음 플레이어로 턴 변경
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

        // 결과 초기화
        currentResults.clear();
        canThrowAgain = false;

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("player", currentPlayer);
        data.put("players", Collections.unmodifiableList(players));
        eventManager.fireEvent(new GameEvent(GameEventType.TURN_CHANGE, data));
    }

    private void endGame() {
        state = GameState.FINISHED;

        // 이벤트 발생
        Map<String, Object> data = new HashMap<>();
        data.put("winner", currentPlayer);
        eventManager.fireEvent(new GameEvent(GameEventType.GAME_OVER, data));
    }

    public void restart() {
        state = GameState.SETUP;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getState() {
        return state;
    }

    public List<YutResult> getCurrentResults() {
        return Collections.unmodifiableList(currentResults);
    }

    public boolean canThrowAgain() {
        return canThrowAgain;
    }

    public void addEventListener(GameEventType type, GameEventListener listener) {
        eventManager.addListener(type, listener);
    }

    public void removeEventListener(GameEventType type, GameEventListener listener) {
        eventManager.removeListener(type, listener);
    }
}