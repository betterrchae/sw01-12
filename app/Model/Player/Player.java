package app.Model.Player;

import app.Model.Horse.Horse;
import app.Model.Horse.HorseGroup;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Player {
  private final String name;
  private final Color color;
  private final List<Horse> horses;

  public Player(String name, Color color, int horseCount) {
    this.name = name;
    this.color = color;
    this.horses = new ArrayList<>(horseCount);

    // 말 초기화
    for (int i = 0; i < horseCount; i++) {
      horses.add(new Horse(i, this));
    }
  }

  public String getName() {
    return name;
  }

  public Color getColor() {
    return color;
  }

  public List<Horse> getHorses() {
    return Collections.unmodifiableList(horses);
  }

  public int getFinishedHorseCount() {
    return (int) horses.stream().filter(Horse::isFinished).count();
  }

  public boolean isAllHorsesFinished() {
    return horses.stream().allMatch(Horse::isFinished);
  }

  public List<Horse> getAvailableHorses() {
    return horses.stream()
        .filter(h -> !h.isFinished() && !h.isInGroup())
        .collect(Collectors.toList());
  }

  public List<HorseGroup> getHorseGroups() {
    Set<HorseGroup> groups = new HashSet<>();
    for (Horse horse : horses) {
      if (horse.isInGroup()) {
        groups.add(horse.getGroup());
      }
    }
    return new ArrayList<>(groups);
  }

  @Override
  public String toString() {
    return "Player{name=" + name + "}";
  }
}
