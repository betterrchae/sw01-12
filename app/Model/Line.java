package View.Code.Model;

import java.util.Objects;

public class Line {
  private final Spot from;
  private final Spot to;

  public Line(Spot from, Spot to) {
    this.from = from;
    this.to = to;
  }

  public Spot getFrom() {
    return from;
  }

  public Spot getTo() {
    return to;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Line line = (Line) obj;
    return (from.equals(line.from) && to.equals(line.to));
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }
}