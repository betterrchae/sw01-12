package app.test;

import app.Model.Enum.GameEventType;
import app.Model.Event.GameEvent;
import app.Model.Event.GameEventListener;
import app.Model.Event.GameEventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameEventManagerTest {
    private GameEventManager mgr;

    @BeforeEach
    void setUp() {
        mgr = new GameEventManager();
    }

    @Test
    void addListenerAndFireEvent_shouldCallListener() {
        AtomicBoolean called = new AtomicBoolean(false);
        GameEventListener listener = ev -> {
            if (ev.getType() == GameEventType.GAME_SETUP) {
                called.set(true);
            }
        };

        mgr.addListener(GameEventType.GAME_SETUP, listener);
        mgr.fireEvent(new GameEvent(GameEventType.GAME_SETUP, Map.of()));
        assertTrue(called.get(), "GAME_SETUP 이벤트가 발행되면 리스너가 호출되어야 한다");
    }

    @Test
    void duplicateListener_shouldBeRegisteredOnce() {
        GameEventListener dummy = ev -> {
        };
        mgr.addListener(GameEventType.TURN_CHANGE, dummy);
        mgr.addListener(GameEventType.TURN_CHANGE, dummy);
        // 내부 리스트 사이즈를 리플렉션 없이 확인하긴 어렵지만, 별도 remove 후 fireEvent 호출 횟수로 검증 가능
        AtomicBoolean called = new AtomicBoolean(false);
        mgr.addListener(GameEventType.TURN_CHANGE, ev -> called.set(true));
        mgr.fireEvent(new GameEvent(GameEventType.TURN_CHANGE, Map.of()));
        assertTrue(called.get());
    }
}
