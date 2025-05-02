package app.Model.Horse;

import app.Model.Player.Player;
import app.Model.Spot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HorseGroup {
  private final List<Horse> horses;
  private Spot currentSpot;

  public HorseGroup(Horse initialHorse) {
    this.horses = new ArrayList<>();
    this.currentSpot = initialHorse.getCurrentSpot();
    addHorse(initialHorse);
  }

  public List<Horse> getHorses() {
    return Collections.unmodifiableList(horses);
  }

  public Spot getCurrentSpot() {
    return currentSpot;
  }

  public void setCurrentSpot(Spot spot) {
    this.currentSpot = spot;

    // 그룹 내 모든 말의 위치 업데이트
    for (Horse horse : horses) {
      horse.setCurrentSpot(spot);
    }
  }

  public int getSize() {
    return horses.size();
  }

  public boolean addHorse(Horse horse) {
    if (horse == null || horses.contains(horse)) {
      return false;
    }

    horses.add(horse);
    horse.setGroup(this);
    return true;
  }

  public boolean removeHorse(Horse horse) {
    if (horse == null || !horses.contains(horse)) {
      return false;
    }

    horses.remove(horse);
    horse.setGroup(null);

    // 그룹에 말이 하나만 남으면 그룹 해제
    if (horses.size() == 1) {
      Horse lastHorse = horses.get(0);
      lastHorse.setGroup(null);
      horses.clear();
      return true;
    }

    return true;
  }

  public boolean move(Spot destination) {
    this.currentSpot = destination;

    // 그룹 내 모든 말 이동
    for (Horse horse : horses) {
      horse.setCurrentSpot(destination);

      if (destination.isFinish()) {
        horse.setFinished(true);
      }
    }

    return true;
  }

  public boolean isAllFinished() {
    return horses.stream().allMatch(Horse::isFinished);
  }

  public Player getOwner() {
    if (horses.isEmpty()) {
      return null;
    }
    return horses.get(0).getOwner();
  }
}
