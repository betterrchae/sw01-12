package app.Model.Horse;

import app.Model.Spot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HorseGroup {
  private final List<Horse> horses;

    public HorseGroup(Horse initialHorse) {
    this.horses = new ArrayList<>();
        addHorse(initialHorse);
  }

  public List<Horse> getHorses() {
    return Collections.unmodifiableList(horses);
  }

  public void addHorse(Horse horse) {
    if (horse == null || horses.contains(horse)) {
      return;
    }

    horses.add(horse);
    horse.setGroup(this);
  }

  public void removeHorse(Horse horse) {
    if (horse == null || !horses.contains(horse)) {
      return;
    }

    horses.remove(horse);
    horse.setGroup(null);

    // 그룹에 말이 하나만 남으면 그룹 해제
    if (horses.size() == 1) {
      Horse lastHorse = horses.get(0);
      lastHorse.setGroup(null);
      horses.clear();
    }

  }

  public boolean move(Spot destination) {

      // 그룹 내 모든 말 이동
    for (Horse horse : horses) {
      horse.setCurrentSpot(destination);

      if (destination.isFinish()) {
        horse.setFinished(true);
      }
    }

    return true;
  }
}
