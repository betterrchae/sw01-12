package View.Code.Enum;

public enum YutResult {
  BACKDO(-1, "빽도"), // 뒤로 한 칸
  DO(1, "도"), // 앞으로 한 칸
  GAE(2, "개"), // 앞으로 두 칸
  GEOL(3, "걸"), // 앞으로 세 칸
  YUT(4, "윷"), // 앞으로 네 칸, 한 번 더 던짐
  MO(5, "모"); // 앞으로 다섯 칸, 한 번 더 던짐

  private final int moveCount;
  private final String displayName;

  YutResult(int moveCount, String displayName) {
    this.moveCount = moveCount;
    this.displayName = displayName;
  }

  public int getMoveCount() {
    return moveCount;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean canThrowAgain() {
    return this == YUT || this == MO;
  }
}