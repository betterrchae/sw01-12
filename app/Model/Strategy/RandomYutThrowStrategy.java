package app.Model.Strategy;

import java.util.Random;

import app.Model.Enum.YutResult;

public class RandomYutThrowStrategy implements YutThrowStrategy {
    private final Random random = new Random();

    @Override
    public YutResult throwYut() {
        // 윷 4개를 던져서 앞/뒤 계산
        int frontCount = 0;
        for (int i = 0; i < 4; i++) {
            if (random.nextBoolean()) {
                frontCount++;
            }
        }

        // 앞면 개수에 따른 결과 결정
        switch (frontCount) {
            case 0:
                return YutResult.MO;
            case 1:
                return YutResult.DO;
            case 2:
                return YutResult.GAE;
            case 3:
                return YutResult.GEOL;
            case 4:
                return YutResult.YUT;
            default:
                // 빽도 (추가 랜덤 확률로 등장)
                if (frontCount == 1 && random.nextFloat() < 0.1f) {
                    return YutResult.BACKDO;
                }
                return YutResult.DO;
        }
    }
}