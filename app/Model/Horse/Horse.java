package app.Model.Horse;

import app.Model.Player.Player;
import app.Model.Spot;
import app.Model.Path;

public class Horse {
  private final int id;
  private final Player owner;
  private Spot currentSpot;
  private HorseGroup group;
  private boolean isFinished;
  private Path currentPath;

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

  public Path getCurrentPath() { return currentPath; }

  public void setGroup(HorseGroup group) {
    this.group = group;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }

  public void setCurrentPath(Path currentPath) { this.currentPath = currentPath; }

  public boolean isInGroup() {
    return group != null;
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