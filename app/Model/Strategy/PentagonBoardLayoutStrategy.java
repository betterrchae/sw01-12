package app.Model.Strategy;

import app.Model.Board;
import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;
import app.Model.Line;
import app.Model.Path;
import app.Model.Spot;
import app.presentation.view.BoardLayoutStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PentagonBoardLayoutStrategy implements BoardLayoutStrategy {
    @Override
    public Board createBoard() {
        List<Spot> spots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Path> paths = new ArrayList<>();

        // 중심점과 화면 크기 설정
        int centerX = 600;
        int centerY = 400;
        int bottomLineWidth = 400; // 밑변의 길이

        // 출발/도착 지점을 포함한 모서리 지점 (더 크게 표시)
        Spot startSpot = new Spot(0, new Point(centerX - 200, centerY + 300), true, true, false);
        Spot corner1 = new Spot(5, new Point(centerX + 200, centerY + 300), true, false, false);
        Spot corner2 = new Spot(10, new Point(centerX + 300, centerY), true, false, false);
        Spot corner3 = new Spot(15, new Point(centerX, centerY - 300), true, false, false);
        Spot corner4 = new Spot(20, new Point(centerX - 300, centerY), true, false, false);
        Spot center = new Spot(25, new Point(centerX, centerY), true, false, false);

        Spot finishSpot = new Spot(36, new Point(centerX - 250, centerY + 300), false, false, true);

        spots.add(startSpot);
        spots.add(corner1);
        spots.add(corner2);
        spots.add(corner3);
        spots.add(corner4);
        spots.add(center);
        spots.add(finishSpot);

        // 최하단 수평 변
        Spot bottom1 = new Spot(1, new Point(centerX - 200 + bottomLineWidth / 5, centerY + 300), false, false, false);
        Spot bottom2 = new Spot(2, new Point(centerX - 200 + bottomLineWidth / 5 * 2, centerY + 300), false, false, false);
        Spot bottom3 = new Spot(3, new Point(centerX - 200 + bottomLineWidth / 5 * 3, centerY + 300), false, false, false);
        Spot bottom4 = new Spot(4, new Point(centerX - 200 + bottomLineWidth / 5 * 4, centerY + 300), false, false, false);

        spots.add(bottom1);
        spots.add(bottom2);
        spots.add(bottom3);
        spots.add(bottom4);

        // 오른쪽 + 아래쪽 변
        Spot right_bottom1 = new Spot(6, new Point(centerX + 200 + 20, centerY + 300 - 60), false, false, false);
        Spot right_bottom2 = new Spot(7, new Point(centerX + 200 + 40, centerY + 300 - 120), false, false, false);
        Spot right_bottom3 = new Spot(8, new Point(centerX + 200 + 60, centerY + 300 - 180), false, false, false);
        Spot right_bottom4 = new Spot(9, new Point(centerX + 200 + 80, centerY + 300 - 240), false, false, false);

        spots.add(right_bottom1);
        spots.add(right_bottom2);
        spots.add(right_bottom3);
        spots.add(right_bottom4);

        // 오른쪽 + 위쪽 변
        Spot right_top1 = new Spot(11, new Point(centerX + 300 - 60, centerY - 60), false, false, false);
        Spot right_top2 = new Spot(12, new Point(centerX + 300 - 120, centerY - 120), false, false, false);
        Spot right_top3 = new Spot(13, new Point(centerX + 300 - 180, centerY - 180), false, false, false);
        Spot right_top4 = new Spot(14, new Point(centerX + 300 - 240, centerY - 240), false, false, false);

        spots.add(right_top1);
        spots.add(right_top2);
        spots.add(right_top3);
        spots.add(right_top4);

        // 왼쪽 + 위쪽 변
        Spot left_top1 = new Spot(16, new Point(centerX - 60, centerY - 300 + 60), false, false, false);
        Spot left_top2 = new Spot(17, new Point(centerX - 120, centerY - 300 + 120), false, false, false);
        Spot left_top3 = new Spot(18, new Point(centerX - 180, centerY - 300 + 180), false, false, false);
        Spot left_top4 = new Spot(19, new Point(centerX - 240, centerY - 300 + 240), false, false, false);

        spots.add(left_top1);
        spots.add(left_top2);
        spots.add(left_top3);
        spots.add(left_top4);

        // 왼쪽 + 아래쪽 변
        Spot left_bottom1 = new Spot(21, new Point(centerX - 200 - 80, centerY + 300 - 240), false, false, false);
        Spot left_bottom2 = new Spot(22, new Point(centerX - 200 - 60, centerY + 300 - 180), false, false, false);
        Spot left_bottom3 = new Spot(23, new Point(centerX - 200 - 40, centerY + 300 - 120), false, false, false);
        Spot left_bottom4 = new Spot(24, new Point(centerX - 200 - 20, centerY + 300 - 60), false, false, false);

        spots.add(left_bottom1);
        spots.add(left_bottom2);
        spots.add(left_bottom3);
        spots.add(left_bottom4);

        // diag1 corner1 -> center
        Spot diag1_1 = new Spot(26, new Point(centerX + 150, centerY + 200), false, false, false);
        Spot diag1_2 = new Spot(27, new Point(centerX + 50, centerY + 100), false, false, false);

        // diag2 corner2 -> center
        Spot diag2_1 = new Spot(28, new Point(centerX + 200, centerY), false, false, false);
        Spot diag2_2 = new Spot(29, new Point(centerX + 100, centerY), false, false, false);

        // diag3 corner3 -> center
        Spot diag3_1 = new Spot(30, new Point(centerX, centerY - 200), false, false, false);
        Spot diag3_2 = new Spot(31, new Point(centerX, centerY - 100), false, false, false);

        // diag4 corner4 -> center
        Spot diag4_1 = new Spot(32, new Point(centerX - 200, centerY), false, false, false);
        Spot diag4_2 = new Spot(33, new Point(centerX - 100, centerY), false, false, false);

        // diag5 start -> center
        Spot diag5_1 = new Spot(34, new Point(centerX - 150, centerY + 200), false, false, false);
        Spot diag5_2 = new Spot(35, new Point(centerX - 50, centerY + 100), false, false, false);

        spots.add(diag1_1);
        spots.add(diag1_2);
        spots.add(diag2_1);
        spots.add(diag2_2);
        spots.add(diag3_1);
        spots.add(diag3_2);
        spots.add(diag4_1);
        spots.add(diag4_2);
        spots.add(diag5_1);
        spots.add(diag5_2);

        // 외곽 선 연결
        // 아래 변
        lines.add(new Line(startSpot, bottom1));
        lines.add(new Line(bottom1, bottom2));
        lines.add(new Line(bottom2, bottom3));
        lines.add(new Line(bottom3, bottom4));
        lines.add(new Line(bottom4, corner1));

        // 오른쪽 아래 변
        lines.add(new Line(corner1, right_bottom1));
        lines.add(new Line(right_bottom1, right_bottom2));
        lines.add(new Line(right_bottom2, right_bottom3));
        lines.add(new Line(right_bottom3, right_bottom4));
        lines.add(new Line(right_bottom4, corner2));

        // 오른쪽 위 변
        lines.add(new Line(corner2, right_top1));
        lines.add(new Line(right_top1, right_top2));
        lines.add(new Line(right_top2, right_top3));
        lines.add(new Line(right_top3, right_top4));
        lines.add(new Line(right_top4, corner3));

        // 왼쪽 위 변
        lines.add(new Line(corner3, left_top1));
        lines.add(new Line(left_top1, left_top2));
        lines.add(new Line(left_top2, left_top3));
        lines.add(new Line(left_top3, left_top4));
        lines.add(new Line(left_top4, corner4));

        // 왼쪽 아래 변
        lines.add(new Line(corner4, left_bottom1));
        lines.add(new Line(left_bottom1, left_bottom2));
        lines.add(new Line(left_bottom2, left_bottom3));
        lines.add(new Line(left_bottom3, left_bottom4));
        lines.add(new Line(left_bottom4, startSpot));

        // 대각선 선 연결
        // 오른쪽 아래 대각선
        lines.add(new Line(corner1, diag1_1));
        lines.add(new Line(diag1_1, diag1_2));
        lines.add(new Line(diag1_2, center));

        // 오른쪽 위 대각선
        lines.add(new Line(corner2, diag2_1));
        lines.add(new Line(diag2_1, diag2_2));
        lines.add(new Line(diag2_2, center));

        // 중앙 위 대각선
        lines.add(new Line(corner3, diag3_1));
        lines.add(new Line(diag3_1, diag3_2));
        lines.add(new Line(diag3_2, center));

        // 왼쪽 위 대각선
        lines.add(new Line(center, diag4_2));
        lines.add(new Line(diag4_2, diag4_1));
        lines.add(new Line(diag4_1, corner4));

        // 왼쪽 아래 대각선
        lines.add(new Line(center, diag5_2));
        lines.add(new Line(diag5_2, diag5_1));
        lines.add(new Line(diag5_1, startSpot));

        // 시작점에서 도착점 연결
        lines.add(new Line(startSpot, finishSpot));

        // ─── 경로(Path) 설정 ───
        // 1) 기본 외곽 경로(main: 0 → … → 24 → finishSpot)
        Path mainPath = new Path("main", false);
        mainPath.addSpot(startSpot);     // 0
        mainPath.addSpot(bottom1);       // 1
        mainPath.addSpot(bottom2);       // 2
        mainPath.addSpot(bottom3);       // 3
        mainPath.addSpot(bottom4);       // 4
        mainPath.addSpot(corner1);       // 5
        mainPath.addSpot(right_bottom1); // 6
        mainPath.addSpot(right_bottom2); // 7
        mainPath.addSpot(right_bottom3); // 8
        mainPath.addSpot(right_bottom4); // 9
        mainPath.addSpot(corner2);       // 10
        mainPath.addSpot(right_top1);    // 11
        mainPath.addSpot(right_top2);    // 12
        mainPath.addSpot(right_top3);    // 13
        mainPath.addSpot(right_top4);    // 14
        mainPath.addSpot(corner3);       // 15
        mainPath.addSpot(left_top1);     // 16
        mainPath.addSpot(left_top2);     // 17
        mainPath.addSpot(left_top3);     // 18
        mainPath.addSpot(left_top4);     // 19
        mainPath.addSpot(corner4);       // 20
        mainPath.addSpot(left_bottom1);  // 21
        mainPath.addSpot(left_bottom2);  // 22
        mainPath.addSpot(left_bottom3);  // 23
        mainPath.addSpot(left_bottom4);  // 24
        mainPath.addSpot(startSpot);  // 0
        mainPath.addSpot(finishSpot);    // 36
        paths.add(mainPath);

        // 2) 코너(5,10,15) → 중앙(25) → 코너4(20) → finish
        Path diag5to20_A = new Path("diag5to20_from5", true);
        diag5to20_A.addSpot(corner1); // 5
        diag5to20_A.addSpot(diag1_1); // 26
        diag5to20_A.addSpot(diag1_2); // 27
        diag5to20_A.addSpot(center);  // 25
        diag5to20_A.addSpot(diag4_2); // 33
        diag5to20_A.addSpot(diag4_1); // 32
        diag5to20_A.addSpot(corner4); // 20
        paths.add(diag5to20_A);

        Path diag10to20_B = new Path("diag10to20_from10", true);
        diag10to20_B.addSpot(corner2); // 10
        diag10to20_B.addSpot(diag2_1); // 28
        diag10to20_B.addSpot(diag2_2); // 29
        diag10to20_B.addSpot(center);  // 25
        diag10to20_B.addSpot(diag4_2); // 33
        diag10to20_B.addSpot(diag4_1); // 32
        diag10to20_B.addSpot(corner4); // 20
        paths.add(diag10to20_B);

        Path diag15to20_C = new Path("diag15to20_from15", true);
        diag15to20_C.addSpot(corner3); // 15
        diag15to20_C.addSpot(diag3_1); // 30
        diag15to20_C.addSpot(diag3_2); // 31
        diag15to20_C.addSpot(center);  // 25
        diag15to20_C.addSpot(diag4_2); // 33
        diag15to20_C.addSpot(diag4_1); // 32
        diag15to20_C.addSpot(corner4); // 20
        paths.add(diag15to20_C);

        // 3) 중앙(25)에 정확히 멈출 경우 → start(0) 로 돌아가서 finish
        Path centerToStart = new Path("centerToStart", true);
        centerToStart.addSpot(center);   // 25
        centerToStart.addSpot(diag5_2);  // 35
        centerToStart.addSpot(diag5_1);  // 34
        centerToStart.addSpot(startSpot); // 0
        centerToStart.addSpot(finishSpot);
        paths.add(centerToStart);

        // 4) 바로 finish 로 가는 경우 (예: 모·윷에서 한 번에)
        Path directFinish = new Path("directFinish", false);
        directFinish.addSpot(finishSpot);
        paths.add(directFinish);

        // ─── nextSpot / nextPath 매핑 ───
        // 외곽 이동 (항상 DO 결과로 한 칸씩)
        for (int i = 0; i < mainPath.getSpots().size() - 1; i++) {
            Spot cur = mainPath.getSpots().get(i);
            Spot nxt = mainPath.getSpots().get(i + 1);
            cur.addNextSpot(YutResult.DO, nxt);
        }

        // 코너 5,10,15 에서 모든 윷 결과(빽도 제외) 시 대각선 진입
        for (YutResult r : YutResult.values()) {
            if (r != YutResult.BACKDO) {
                corner1.addNextPath(r, diag5to20_A);
                corner2.addNextPath(r, diag10to20_B);
                corner3.addNextPath(r, diag15to20_C);
                // 중앙으로 정확히 착지했을 때
                center.addNextPath(r, centerToStart);
            }
        }

        return new Board(BoardType.PENTAGON, spots, lines, paths);
    }
}
