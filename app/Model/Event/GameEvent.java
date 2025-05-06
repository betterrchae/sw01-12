package app.Model.Event;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import app.Model.Enum.GameEventType;

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

  public Map<String, Object> getData() {
    return Collections.unmodifiableMap(data);
  }

  public Object get(String key) {
    return data.get(key);
  }

  @Override
  public String toString() {
    return "GameEvent{type=" + type + ", data=" + data + "}";
  }
}