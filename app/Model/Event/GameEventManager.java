package app.Model.Event;

import app.Model.Enum.GameEventType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GameEventManager {
    private final Map<GameEventType, List<GameEventListener>> listeners;

    public GameEventManager() {
        this.listeners = new EnumMap<>(GameEventType.class);
        for (GameEventType type : GameEventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public void addListener(GameEventType type, GameEventListener listener) {
        if (!listeners.get(type).contains(listener)) {
            listeners.get(type).add(listener);
        }
    }

    public void removeListener(GameEventType type, GameEventListener listener) {
        listeners.get(type).remove(listener);
    }

    public void fireEvent(GameEvent event) {
        List<GameEventListener> typeListeners = listeners.get(event.getType());
        for (GameEventListener listener : typeListeners) {
            listener.onGameEvent(event);
        }
    }
}