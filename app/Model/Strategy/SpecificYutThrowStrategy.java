package app.Model.Strategy;

import app.Model.Enum.YutResult;

public class SpecificYutThrowStrategy implements YutThrowStrategy {
    private final YutResult specificResult;

    public SpecificYutThrowStrategy(YutResult specificResult) {
        this.specificResult = specificResult;
    }

    @Override
    public YutResult throwYut() {
        return specificResult;
    }
}