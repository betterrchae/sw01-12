package app.Model.Event;

import app.Model.Enum.GameEventType;

import java.util.HashMap;
import java.util.Map;

public class GameEvent {
    private final GameEventType type;
    private final Map<String, Object> data;

    public GameEvent(GameEventType type, Map<String, Object> data) {
        this.type = type;
        this.data = new HashMap<>(data);
    }

    public GameEventType getType() {
        return type;
    }

    public Object get(String key) {
        return data.get(key);
    }

    @Override
    public String toString() {
        return "GameEvent{type=" + type + ", data=" + data + "}";
    }
}