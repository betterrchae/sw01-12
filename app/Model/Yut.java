package app.Model;

import app.Model.Enum.YutResult;
import app.Model.Strategy.RandomYutThrowStrategy;
import app.Model.Strategy.YutThrowStrategy;

public class Yut {
    private YutThrowStrategy strategy;

    public Yut() {
        this.strategy = new RandomYutThrowStrategy();
    }

    public void setStrategy(YutThrowStrategy strategy) {
        this.strategy = strategy;
    }

    public YutResult throwYut() {
        return strategy.throwYut();
    }
}