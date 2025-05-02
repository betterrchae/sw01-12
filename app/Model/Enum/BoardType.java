package app.Model.Enum;

public enum BoardType {
  SQUARE("사각형"),
  PENTAGON("오각형"),
  HEXAGON("육각형");

  private final String displayName;

  BoardType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}