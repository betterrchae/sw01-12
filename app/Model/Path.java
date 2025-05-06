package app.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Path {
  private final List<Spot> spots;
  private final boolean isShortcut;
  private final String name;

  public Path(String name, boolean isShortcut) {
    this.name = name;
    this.spots = new ArrayList<>();
    this.isShortcut = isShortcut;
  }

  public void addSpot(Spot spot) {
    spots.add(spot);
  }

  public List<Spot> getSpots() {
    return Collections.unmodifiableList(spots);
  }

  public boolean isShortcut() {
    return isShortcut;
  }

  public String getName() {
    return name;
  }

  public Spot getFirstSpot() {
    return spots.isEmpty() ? null : spots.get(0);
  }
}