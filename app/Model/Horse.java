package View.Code.Model;

public class Horse {
  private final int id;
  private final Player owner;
  private Spot currentSpot;
  private HorseGroup group;
  private boolean isFinished;

  public Horse(int id, Player owner) {
    this.id = id;
    this.owner = owner;
    this.currentSpot = null;
    this.group = null;
    this.isFinished = false;
  }

  public int getId() {
    return id;
  }

  public Player getOwner() {
    return owner;
  }

  public Spot getCurrentSpot() {
    return currentSpot;
  }

  public void setCurrentSpot(Spot spot) {
    this.currentSpot = spot;
  }

  public HorseGroup getGroup() {
    return group;
  }

  public void setGroup(HorseGroup group) {
    this.group = group;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }

  public boolean isInGroup() {
    return group != null;
  }

  public void leaveGroup() {
    if (group != null) {
      group.removeHorse(this);
      this.group = null;
    }
  }

  public boolean move(Spot destination) {
    if (isFinished) {
      return false;
    }

    if (isInGroup()) {
      // 그룹 내에 있는 말은 그룹 단위로 이동
      return false;
    }

    this.currentSpot = destination;

    if (destination.isFinish()) {
      this.isFinished = true;
    }

    return true;
  }

  @Override
  public String toString() {
    return "Horse{id=" + id + ", owner=" + owner.getName() + "}";
  }
}