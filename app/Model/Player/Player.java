package app.Model.Player;

import app.Model.Horse.Horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public String toString() {
        return "Player{name=" + name + "}";
    }
}
