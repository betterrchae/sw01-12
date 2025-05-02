package View.Code.Model;

import View.Code.Enum.YutResult;
import View.Code.Strategy.RandomYutThrowStrategy;
import View.Code.Strategy.YutThrowStrategy;

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