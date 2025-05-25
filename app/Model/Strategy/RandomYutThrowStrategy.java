package app.Model.Strategy;

import app.Model.Enum.YutResult;

import java.util.Random;

public class RandomYutThrowStrategy implements YutThrowStrategy {
    private final Random random = new Random();

    @Override
    public YutResult throwYut() {
        int frontCount = 0;
        for (int i = 0; i < 4; i++) {
            if (random.nextBoolean()) {
                frontCount++;
            }
        }

        // 앞면 개수에 따른 결과 결정
        return switch (frontCount) {
            case 0 -> YutResult.MO;
            case 2 -> YutResult.GAE;
            case 3 -> YutResult.GEOL;
            case 4 -> YutResult.YUT;
            default -> YutResult.DO;
        };
    }
}