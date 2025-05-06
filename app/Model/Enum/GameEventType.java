package app.Model.Enum;

public enum GameEventType {
  GAME_SETUP("게임 설정"),
  YUT_THROW("윷 던지기"),
  HORSE_MOVE("말 이동"),
  CAPTURE("말 잡기"),
  GROUP("말 업기"),
  TURN_CHANGE("턴 변경"),
  GAME_OVER("게임 종료");

  private final String displayName;

  GameEventType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
