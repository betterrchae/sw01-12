package app.test;

import app.Model.Enum.YutResult;
import app.Model.Strategy.RandomYutThrowStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomYutThrowStrategyTest {

    private RandomYutThrowStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new RandomYutThrowStrategy();
    }

    /**
     * RandomYutThrowStrategy 내부의 Random 객체를 주입하기 위한 유틸.
     */
    private void injectRandom(Random stub) throws Exception {
        Field rndField = RandomYutThrowStrategy.class.getDeclaredField("random");
        rndField.setAccessible(true);
        rndField.set(strategy, stub);
    }

    @Test
    void testThrowYut_MO_whenZeroTrues() throws Exception {
        // 4번 모두 false 반환 -> frontCount=0 -> MO
        Random stub = new Random() {
            int calls;

            @Override
            public boolean nextBoolean() {
                calls++;
                return false;
            }
        };
        injectRandom(stub);
        assertEquals(YutResult.MO, strategy.throwYut());
    }

    @Test
    void testThrowYut_DO_whenOneTrue() throws Exception {
        // exactly one true among 4
        Random stub = new Random() {
            int calls;

            @Override
            public boolean nextBoolean() {
                // 첫 번째만 true, 나머지 false
                return calls++ == 0;
            }
        };
        injectRandom(stub);
        assertEquals(YutResult.DO, strategy.throwYut());
    }

    @Test
    void testThrowYut_GAE_whenTwoTrues() throws Exception {
        // exactly two trues
        Random stub = new Random() {
            int calls;

            @Override
            public boolean nextBoolean() {
                // 첫 두 번 true, 나머지 false
                return calls++ < 2;
            }
        };
        injectRandom(stub);
        assertEquals(YutResult.GAE, strategy.throwYut());
    }

    @Test
    void testThrowYut_GEOL_whenThreeTrues() throws Exception {
        // exactly three trues
        Random stub = new Random() {
            int calls;

            @Override
            public boolean nextBoolean() {
                // 첫 세 번 true
                return calls++ < 3;
            }
        };
        injectRandom(stub);
        assertEquals(YutResult.GEOL, strategy.throwYut());
    }

    @Test
    void testThrowYut_YUT_whenFourTrues() throws Exception {
        // 모든 nextBoolean()이 true
        Random stub = new Random() {
            @Override
            public boolean nextBoolean() {
                return true;
            }
        };
        injectRandom(stub);
        assertEquals(YutResult.YUT, strategy.throwYut());
    }

    @Test
    void testThrowYut_MultipleRunsCoverAllCases() {
        // 실제 랜덤 환경에서도 예외 없이 YutResult 값만 반환되는지 확인
        for (int i = 0; i < 1000; i++) {
            YutResult r = strategy.throwYut();
            assertNotNull(r, "결과가 null이면 안 됩니다");
            // 가능한 값인지 체크
            assertTrue(r == YutResult.MO || r == YutResult.DO || r == YutResult.GAE || r == YutResult.GEOL || r == YutResult.YUT, "알 수 없는 결과: " + r);
        }
    }
}
