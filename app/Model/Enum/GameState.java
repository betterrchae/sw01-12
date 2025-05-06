package app.Model.Enum;

public enum GameState {
  SETUP("설정"), // 게임 설정 중
  IN_PROGRESS("진행 중"), // 게임 진행 중
  FINISHED("종료"); // 게임 종료

  private final String displayName;

  GameState(String displayName) {
    this.displayName = displayName;
  }
}