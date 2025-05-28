package app.Model.Strategy;

import app.Model.Board;
import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;
import app.Model.Line;
import app.Model.Path;
import app.Model.Spot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexagonBoardLayoutStrategy implements BoardLayoutStrategy {
    private static void centerPath_move(Spot center, Path centerToStart) {
        for (YutResult r : YutResult.values()) {
            if (r != YutResult.BACKDO) {
                center.addNextPath(r, centerToStart);
            }
        }
    }

    private static void diagPath_move(Spot corner1, Path diagFrom5, Spot corner2, Path diagFrom10, Spot corner3, Path diagFrom15, Spot corner4, Path diagFrom20) {
        for (YutResult r : YutResult.values()) {
            if (r != YutResult.BACKDO) {
                corner1.addNextPath(r, diagFrom5);
                corner2.addNextPath(r, diagFrom10);
                corner3.addNextPath(r, diagFrom15);
                corner4.addNextPath(r, diagFrom20);
            }
        }
    }

    private static void mainPath_move(Path mainPath) {
        for (int i = 0; i < mainPath.getSpots().size() - 1; i++) {
            Spot cur = mainPath.getSpots().get(i);
            Spot nxt = mainPath.getSpots().get(i + 1);
            cur.addNextSpot(YutResult.DO, nxt);
        }
    }


    private static void left_top_Spots(List<Spot> spots, Spot left_top1, Spot left_top2, Spot left_top3, Spot left_top4) {
        spots.add(left_top1);
        spots.add(left_top2);
        spots.add(left_top3);
        spots.add(left_top4);
    }

    private static void top_Spots(List<Spot> spots, Spot top1, Spot top2, Spot top3, Spot top4) {
        spots.add(top1);
        spots.add(top2);
        spots.add(top3);
        spots.add(top4);
    }

    private static void right_top_Spots(List<Spot> spots, Spot right_top1, Spot right_top2, Spot right_top3, Spot right_top4) {
        spots.add(right_top1);
        spots.add(right_top2);
        spots.add(right_top3);
        spots.add(right_top4);
    }

    private static void right_bottom_Spots(List<Spot> spots, Spot right_bottom1, Spot right_bottom2, Spot right_bottom3, Spot right_bottom4) {
        spots.add(right_bottom1);
        spots.add(right_bottom2);
        spots.add(right_bottom3);
        spots.add(right_bottom4);
    }

    private static void bottom_Spots(List<Spot> spots, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4) {
        spots.add(bottom1);
        spots.add(bottom2);
        spots.add(bottom3);
        spots.add(bottom4);
    }

    private static void left_bottom_Spots(List<Spot> spots, Spot left_bottom1, Spot left_bottom2, Spot left_bottom3, Spot left_bottom4) {
        spots.add(left_bottom1);
        spots.add(left_bottom2);
        spots.add(left_bottom3);
        spots.add(left_bottom4);
    }


    private static Path getCenterToStart(Spot center, Spot diag6_2, Spot diag6_1, Spot startSpot, Spot finishSpot, List<Path> paths) {
        Path centerToStart = new Path("centerToStart", true);
        centerToStart.addSpot(center);    // 30
        centerToStart.addSpot(diag6_2);   // 42
        centerToStart.addSpot(diag6_1);   // 41
        centerToStart.addSpot(startSpot); // 0
        centerToStart.addSpot(finishSpot);// 43
        paths.add(centerToStart);
        return centerToStart;
    }

    private static Path getDiagFrom20(Spot corner4, Spot diag4_1, Spot diag4_2, Spot center, Spot diag5_2, Spot diag5_1, Spot corner5, List<Path> paths) {
        Path diagFrom20 = new Path("diagFrom20", true);
        diagFrom20.addSpot(corner4);   // 20
        diagFrom20.addSpot(diag4_1);   // 37
        diagFrom20.addSpot(diag4_2);   // 38
        diagFrom20.addSpot(center);    // 30
        diagFrom20.addSpot(diag5_2);   // 40
        diagFrom20.addSpot(diag5_1);   // 39
        diagFrom20.addSpot(corner5);   // 25
        paths.add(diagFrom20);
        return diagFrom20;
    }

    private static Path getDiagFrom15(Spot corner3, Spot diag3_1, Spot diag3_2, Spot center, Spot diag5_2, Spot diag5_1, Spot corner5, List<Path> paths) {
        Path diagFrom15 = new Path("diagFrom15", true);
        diagFrom15.addSpot(corner3);   // 15
        diagFrom15.addSpot(diag3_1);   // 35
        diagFrom15.addSpot(diag3_2);   // 36
        diagFrom15.addSpot(center);    // 30
        diagFrom15.addSpot(diag5_2);   // 40
        diagFrom15.addSpot(diag5_1);   // 39
        diagFrom15.addSpot(corner5);   // 25
        paths.add(diagFrom15);
        return diagFrom15;
    }

    private static Path getDiagFrom10(Spot corner2, Spot diag2_1, Spot diag2_2, Spot center, Spot diag5_2, Spot diag5_1, Spot corner5, List<Path> paths) {
        Path diagFrom10 = new Path("diagFrom10", true);
        diagFrom10.addSpot(corner2);   // 10
        diagFrom10.addSpot(diag2_1);   // 33
        diagFrom10.addSpot(diag2_2);   // 34
        diagFrom10.addSpot(center);    // 30
        diagFrom10.addSpot(diag5_2);   // 40
        diagFrom10.addSpot(diag5_1);   // 39
        diagFrom10.addSpot(corner5);   // 25
        paths.add(diagFrom10);
        return diagFrom10;
    }

    private static Path getDiagFrom5(Spot corner1, Spot diag1_1, Spot diag1_2, Spot center, Spot diag5_2, Spot diag5_1, Spot corner5, List<Path> paths) {
        Path diagFrom5 = new Path("diagFrom5", true);
        diagFrom5.addSpot(corner1);   // 5
        diagFrom5.addSpot(diag1_1);    // 31
        diagFrom5.addSpot(diag1_2);    // 32
        diagFrom5.addSpot(center);     // 30
        diagFrom5.addSpot(diag5_2);    // 40  ← 추가
        diagFrom5.addSpot(diag5_1);    // 39  ← 추가
        diagFrom5.addSpot(corner5);    // 25
        paths.add(diagFrom5);
        return diagFrom5;
    }

    private static Path getMainPath(Spot startSpot, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot corner1, Spot right_bottom1, Spot right_bottom2, Spot right_bottom3, Spot right_bottom4, Spot corner2, Spot right_top1, Spot right_top2, Spot right_top3, Spot right_top4, Spot corner3, Spot top1, Spot top2, Spot top3, Spot top4, Spot corner4, Spot left_top1, Spot left_top2, Spot left_top3, Spot left_top4, Spot corner5, Spot left_bottom1, Spot left_bottom2, Spot left_bottom3, Spot left_bottom4, Spot finishSpot, List<Path> paths) {
        Path mainPath = new Path("main", false);
        mainPath.addSpot(startSpot);  // 0
        mainPath.addSpot(bottom1);    // 1
        mainPath.addSpot(bottom2);    // 2
        mainPath.addSpot(bottom3);    // 3
        mainPath.addSpot(bottom4);    // 4
        mainPath.addSpot(corner1);    // 5
        mainPath.addSpot(right_bottom1);  // 6
        mainPath.addSpot(right_bottom2);  // 7
        mainPath.addSpot(right_bottom3);  // 8
        mainPath.addSpot(right_bottom4);  // 9
        mainPath.addSpot(corner2);    // 10
        mainPath.addSpot(right_top1);     // 11
        mainPath.addSpot(right_top2);     // 12
        mainPath.addSpot(right_top3);     // 13
        mainPath.addSpot(right_top4);     // 14
        mainPath.addSpot(corner3);    // 15
        mainPath.addSpot(top1);      // 16
        mainPath.addSpot(top2);      // 17
        mainPath.addSpot(top3);      // 18
        mainPath.addSpot(top4);      // 19
        mainPath.addSpot(corner4);    // 20
        mainPath.addSpot(left_top1);   // 21
        mainPath.addSpot(left_top2);   // 22
        mainPath.addSpot(left_top3);   // 23
        mainPath.addSpot(left_top4);   // 24
        mainPath.addSpot(corner5);    // 25
        mainPath.addSpot(left_bottom1);    // 26
        mainPath.addSpot(left_bottom2);    // 27
        mainPath.addSpot(left_bottom3);    // 28
        mainPath.addSpot(left_bottom4);    // 29
        mainPath.addSpot(startSpot);  // 다시 0
        mainPath.addSpot(finishSpot); // 43
        paths.add(mainPath);
        return mainPath;
    }

    private static void diag_Lines(List<Line> lines, Spot corner1, Spot diag1_1, Spot diag1_2, Spot center, Spot corner2, Spot diag2_1, Spot diag2_2, Spot corner3, Spot diag3_1, Spot diag3_2, Spot corner4, Spot diag4_1, Spot diag4_2, Spot diag5_2, Spot diag5_1, Spot corner5, Spot diag6_2, Spot diag6_1, Spot startSpot) {
        // 오른쪽 아래 대각선
        lines.add(new Line(corner1, diag1_1));
        lines.add(new Line(diag1_1, diag1_2));
        lines.add(new Line(diag1_2, center));

        // 오른쪽 중앙 대각선
        lines.add(new Line(corner2, diag2_1));
        lines.add(new Line(diag2_1, diag2_2));
        lines.add(new Line(diag2_2, center));

        // 오른쪽 위 대각선
        lines.add(new Line(corner3, diag3_1));
        lines.add(new Line(diag3_1, diag3_2));
        lines.add(new Line(diag3_2, center));

        // 왼쪽 위 대각선
        lines.add(new Line(corner4, diag4_1));
        lines.add(new Line(diag4_1, diag4_2));
        lines.add(new Line(diag4_2, center));

        // 왼쪽 중앙 대각선
        lines.add(new Line(center, diag5_2));
        lines.add(new Line(diag5_2, diag5_1));
        lines.add(new Line(diag5_1, corner5));

        // 왼쪽 아래 대각선
        lines.add(new Line(center, diag6_2));
        lines.add(new Line(diag6_2, diag6_1));
        lines.add(new Line(diag6_1, startSpot));
    }

    private static void side_Lines(List<Line> lines, Spot startSpot, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot corner1, Spot right_bottom1, Spot right_bottom2, Spot right_bottom3, Spot right_bottom4, Spot corner2, Spot right_top1, Spot right_top2, Spot right_top3, Spot right_top4, Spot corner3, Spot top1, Spot top2, Spot top3, Spot top4, Spot corner4, Spot left_top1, Spot left_top2, Spot left_top3, Spot left_top4, Spot corner5, Spot left_bottom1, Spot left_bottom2, Spot left_bottom3, Spot left_bottom4) {
        bottom_Lines(lines, startSpot, bottom1, bottom2, bottom3, bottom4, corner1);

        // 오른쪽 아래 변
        right_bottom_Lines(lines, corner1, right_bottom1, right_bottom2, right_bottom3, right_bottom4, corner2);

        // 오른쪽 위 변
        right_top_Lines(lines, corner2, right_top1, right_top2, right_top3, right_top4, corner3);

        // 윗변
        top_Lines(lines, corner3, top1, top2, top3, top4, corner4);

        // 왼쪽 위 변
        left_top_Lines(lines, corner4, left_top1, left_top2, left_top3, left_top4, corner5);

        // 왼쪽 아래 변
        lef_bottom_Lines(lines, corner5, left_bottom1, left_bottom2, left_bottom3, left_bottom4, startSpot);
    }

    private static void lef_bottom_Lines(List<Line> lines, Spot corner5, Spot left_bottom1, Spot left_bottom2, Spot left_bottom3, Spot left_bottom4, Spot startSpot) {
        lines.add(new Line(corner5, left_bottom1));
        lines.add(new Line(left_bottom1, left_bottom2));
        lines.add(new Line(left_bottom2, left_bottom3));
        lines.add(new Line(left_bottom3, left_bottom4));
        lines.add(new Line(left_bottom4, startSpot));
    }

    private static void left_top_Lines(List<Line> lines, Spot corner4, Spot left_top1, Spot left_top2, Spot left_top3, Spot left_top4, Spot corner5) {
        lines.add(new Line(corner4, left_top1));
        lines.add(new Line(left_top1, left_top2));
        lines.add(new Line(left_top2, left_top3));
        lines.add(new Line(left_top3, left_top4));
        lines.add(new Line(left_top4, corner5));
    }

    private static void top_Lines(List<Line> lines, Spot corner3, Spot top1, Spot top2, Spot top3, Spot top4, Spot corner4) {
        lines.add(new Line(corner3, top1));
        lines.add(new Line(top1, top2));
        lines.add(new Line(top2, top3));
        lines.add(new Line(top3, top4));
        lines.add(new Line(top4, corner4));
    }

    private static void right_top_Lines(List<Line> lines, Spot corner2, Spot right_top1, Spot right_top2, Spot right_top3, Spot right_top4, Spot corner3) {
        lines.add(new Line(corner2, right_top1));
        lines.add(new Line(right_top1, right_top2));
        lines.add(new Line(right_top2, right_top3));
        lines.add(new Line(right_top3, right_top4));
        lines.add(new Line(right_top4, corner3));
    }

    private static void right_bottom_Lines(List<Line> lines, Spot corner1, Spot right_bottom1, Spot right_bottom2, Spot right_bottom3, Spot right_bottom4, Spot corner2) {
        lines.add(new Line(corner1, right_bottom1));
        lines.add(new Line(right_bottom1, right_bottom2));
        lines.add(new Line(right_bottom2, right_bottom3));
        lines.add(new Line(right_bottom3, right_bottom4));
        lines.add(new Line(right_bottom4, corner2));
    }

    private static void bottom_Lines(List<Line> lines, Spot startSpot, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot corner1) {
        lines.add(new Line(startSpot, bottom1));
        lines.add(new Line(bottom1, bottom2));
        lines.add(new Line(bottom2, bottom3));
        lines.add(new Line(bottom3, bottom4));
        lines.add(new Line(bottom4, corner1));
    }

    private static void diag_Spots(List<Spot> spots, Spot diag1_1, Spot diag1_2, Spot diag2_1, Spot diag2_2, Spot diag3_1, Spot diag3_2, Spot diag4_1, Spot diag4_2, Spot diag5_1, Spot diag5_2, Spot diag6_1, Spot diag6_2) {
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
        spots.add(diag6_1);
        spots.add(diag6_2);
    }

    private static void main_Spots(List<Spot> spots, Spot startSpot, Spot corner1, Spot corner2, Spot corner3, Spot corner4, Spot corner5, Spot center, Spot finishSpot) {
        spots.add(startSpot);
        spots.add(corner1);
        spots.add(corner2);
        spots.add(corner3);
        spots.add(corner4);
        spots.add(corner5);
        spots.add(center);
        spots.add(finishSpot);
    }
    @Override
    public LayoutResult createLayout() {
        List<Spot> spots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        Map<Spot,Point> spotPositions = new HashMap<>();

        // 중심점과 화면 크기 설정
        int startX = 300;
        int startY = 700;
        int horizontalLineWidth = 400; // 한변의 길이
        int height = 600;

        int horizontalLineGrid = horizontalLineWidth / 5; // 아랫변 스팟들 간격

        int centerX = startX + horizontalLineWidth / 2;
        int centerY = startY - height / 2;

        // 출발/도착 지점을 포함한 모서리 지점 (더 크게 표시)
        Spot startSpot = new Spot(0, true, true, false);
        spotPositions.put(startSpot, new Point(startX, startY));
        Spot corner1 = new Spot(5, true, false, false);
        spotPositions.put(corner1,new Point(startX + horizontalLineWidth, startY));
        Spot corner2 = new Spot(10, true, false, false);
        spotPositions.put(corner2, new Point(startX + horizontalLineWidth * 3 / 2, startY - height / 2));
        Spot corner3 = new Spot(15, true, false, false);
        spotPositions.put(corner3, new Point(startX + horizontalLineWidth, startY - height));
        Spot corner4 = new Spot(20, true, false, false);
        spotPositions.put(corner4, new Point(startX, startY - height));
        Spot corner5 = new Spot(25, true, false, false);
        spotPositions.put(corner5,new Point(startX - horizontalLineWidth / 2, startY - height / 2));
        Spot center = new Spot(30, true, false, false);
        spotPositions.put(center, new Point(centerX, centerY));

        Spot finishSpot = new Spot(43, false, false, true);
        spotPositions.put(finishSpot,new Point(startX - 65, startY));

        main_Spots(spots, startSpot, corner1, corner2, corner3, corner4, corner5, center, finishSpot);

        // 아랫 변
        Spot bottom1 = new Spot(1, false, false, false);
        spotPositions.put(bottom1,new Point(startX + horizontalLineGrid, startY));
        Spot bottom2 = new Spot(2, false, false, false);
        spotPositions.put(bottom2, new Point(startX + horizontalLineGrid * 2, startY));
        Spot bottom3 = new Spot(3, false, false, false);
        spotPositions.put(bottom3, new Point(startX + horizontalLineGrid * 3, startY));
        Spot bottom4 = new Spot(4, false, false, false);
        spotPositions.put(bottom4,new Point(startX + horizontalLineGrid * 4, startY));

        bottom_Spots(spots, bottom1, bottom2, bottom3, bottom4);

        // 오른쪽 + 아래쪽 변
        int heightGrid = height / 2 / 5;
        int widthGrid = horizontalLineWidth / 2 / 5;

        Spot right_bottom1 = new Spot(6, false, false, false);
        spotPositions.put(right_bottom1, new Point(startX + horizontalLineWidth + widthGrid, startY - heightGrid));
        Spot right_bottom2 = new Spot(7, false, false, false);
        spotPositions.put(right_bottom2,new Point(startX + horizontalLineWidth + widthGrid * 2, startY - heightGrid * 2));
        Spot right_bottom3 = new Spot(8, false, false, false);
        spotPositions.put(right_bottom3,new Point(startX + horizontalLineWidth + widthGrid * 3, startY - heightGrid * 3));
        Spot right_bottom4 = new Spot(9, false, false, false);
        spotPositions.put(right_bottom4,new Point(startX + horizontalLineWidth + widthGrid * 4, startY - heightGrid * 4));

        // 오른쪽 + 위쪽 변
        Spot right_top1 = new Spot(11, false, false, false);
        spotPositions.put(right_top1,new Point(startX + horizontalLineWidth + widthGrid * 4, startY - height / 2 - heightGrid));
        Spot right_top2 = new Spot(12, false, false, false);
        spotPositions.put(right_top2,new Point(startX + horizontalLineWidth + widthGrid * 3, startY - height / 2 - heightGrid * 2));
        Spot right_top3 = new Spot(13, false, false, false);
        spotPositions.put(right_top3, new Point(startX + horizontalLineWidth + widthGrid * 2, startY - height / 2 - heightGrid * 3));
        Spot right_top4 = new Spot(14, false, false, false);
        spotPositions.put(right_top4, new Point(startX + horizontalLineWidth + widthGrid, startY - height / 2 - heightGrid * 4));

        // 윗 변
        Spot top1 = new Spot(16, false, false, false);
        spotPositions.put(top1, new Point(startX + horizontalLineGrid * 4, startY - height));
        Spot top2 = new Spot(17, false, false, false);
        spotPositions.put(top2,new Point(startX + horizontalLineGrid * 3, startY - height));
        Spot top3 = new Spot(18, false, false, false);
        spotPositions.put(top3,new Point(startX + horizontalLineGrid * 2, startY - height));
        Spot top4 = new Spot(19, false, false, false);
        spotPositions.put(top4,new Point(startX + horizontalLineGrid, startY - height));

        // // 왼쪽 + 위쪽 변
        Spot left_top1 = new Spot(21, false, false, false);
        spotPositions.put(left_top1,new Point(startX - widthGrid, startY - height / 2 - heightGrid * 4));
        Spot left_top2 = new Spot(22, false, false, false);
        spotPositions.put(left_top2,new Point(startX - widthGrid * 2, startY - height / 2 - heightGrid * 3));
        Spot left_top3 = new Spot(23, false, false, false);
        spotPositions.put(left_top3, new Point(startX - widthGrid * 3, startY - height / 2 - heightGrid * 2));
        Spot left_top4 = new Spot(24, false, false, false);
        spotPositions.put(left_top4, new Point(startX - widthGrid * 4, startY - height / 2 - heightGrid));

        // // 왼쪽 + 아래쪽 변
        Spot left_bottom1 = new Spot(26, false, false, false);
        spotPositions.put(left_bottom1, new Point(startX - widthGrid * 4, startY - heightGrid * 4));
        Spot left_bottom2 = new Spot(27, false, false, false);
        spotPositions.put(left_bottom2, new Point(startX - widthGrid * 3, startY - heightGrid * 3));
        Spot left_bottom3 = new Spot(28, false, false, false);
        spotPositions.put(left_bottom3, new Point(startX - widthGrid * 2, startY - heightGrid * 2));
        Spot left_bottom4 = new Spot(29, false, false, false);
        spotPositions.put(left_bottom4, new Point(startX - widthGrid, startY - heightGrid));

        // 대각선
        Spot diag1_1 = new Spot(31, false, false, false);
        spotPositions.put(diag1_1, new Point(startX + horizontalLineWidth - 65, startY - 100));
        Spot diag1_2 = new Spot(32, false, false, false);
        spotPositions.put(diag1_2, new Point(startX + horizontalLineWidth - 130, startY - 200));
        Spot diag2_1 = new Spot(33, false, false, false);
        spotPositions.put(diag2_1, new Point(startX + horizontalLineWidth / 2 + 280, startY - height / 2));
        Spot diag2_2 = new Spot(34, false, false, false);
        spotPositions.put(diag2_2, new Point(startX + horizontalLineWidth / 2 + 140, startY - height / 2));
        Spot diag3_1 = new Spot(35, false, false, false);
        spotPositions.put(diag3_1, new Point(startX + horizontalLineWidth - 65, startY - height + 100));
        Spot diag3_2 = new Spot(36, false, false, false);
        spotPositions.put(diag3_2, new Point(startX + horizontalLineWidth - 130, startY - height + 200));
        Spot diag4_1 = new Spot(37, false, false, false);
        spotPositions.put(diag4_1, new Point(startX + 65, startY - height + 100));
        Spot diag4_2 = new Spot(38, false, false, false);
        spotPositions.put(diag4_2, new Point(startX + 130, startY - height + 200));
        Spot diag5_1 = new Spot(39, false, false, false);
        spotPositions.put(diag5_1, new Point(startX + horizontalLineWidth / 2 - 280, startY - height / 2));
        Spot diag5_2 = new Spot(40, false, false, false);
        spotPositions.put(diag5_2, new Point(startX + horizontalLineWidth / 2 - 140, startY - height / 2));
        Spot diag6_1 = new Spot(41, false, false, false);
        spotPositions.put(diag6_1, new Point(startX + 65, startY - 100));
        Spot diag6_2 = new Spot(42, false, false, false);
        spotPositions.put(diag6_2, new Point(startX + 130, startY - 200));

        right_bottom_Spots(spots, right_bottom1, right_bottom2, right_bottom3, right_bottom4);
        right_top_Spots(spots, right_top1, right_top2, right_top3, right_top4);
        top_Spots(spots, top1, top2, top3, top4);
        left_top_Spots(spots, left_top1, left_top2, left_top3, left_top4);
        left_bottom_Spots(spots, left_bottom1, left_bottom2, left_bottom3, left_bottom4);
        diag_Spots(spots, diag1_1, diag1_2, diag2_1, diag2_2, diag3_1, diag3_2, diag4_1, diag4_2, diag5_1, diag5_2, diag6_1, diag6_2);

        // 외곽 선 연결
        // 아래 변
        side_Lines(lines, startSpot, bottom1, bottom2, bottom3, bottom4, corner1, right_bottom1, right_bottom2, right_bottom3, right_bottom4, corner2, right_top1, right_top2, right_top3, right_top4, corner3, top1, top2, top3, top4, corner4, left_top1, left_top2, left_top3, left_top4, corner5, left_bottom1, left_bottom2, left_bottom3, left_bottom4);

        // 대각선 선 연결
        diag_Lines(lines, corner1, diag1_1, diag1_2, center, corner2, diag2_1, diag2_2, corner3, diag3_1, diag3_2, corner4, diag4_1, diag4_2, diag5_2, diag5_1, corner5, diag6_2, diag6_1, startSpot);

        // 시작점에서 도착점 연결
        lines.add(new Line(startSpot, finishSpot));


        // ─── 1) 메인 패스 정의 ───
        Path mainPath = getMainPath(startSpot, bottom1, bottom2, bottom3, bottom4, corner1, right_bottom1, right_bottom2, right_bottom3, right_bottom4, corner2, right_top1, right_top2, right_top3, right_top4, corner3, top1, top2, top3, top4, corner4, left_top1, left_top2, left_top3, left_top4, corner5, left_bottom1, left_bottom2, left_bottom3, left_bottom4, finishSpot, paths);

// ─── 2) 중앙선(diagonal) 정의: 5,10,15,20 → 30(center) → 25(corner5) ───
        Path diagFrom5 = getDiagFrom5(corner1, diag1_1, diag1_2, center, diag5_2, diag5_1, corner5, paths);

// 10번 코너에서도 동일하게
        Path diagFrom10 = getDiagFrom10(corner2, diag2_1, diag2_2, center, diag5_2, diag5_1, corner5, paths);

// 15번 코너
        Path diagFrom15 = getDiagFrom15(corner3, diag3_1, diag3_2, center, diag5_2, diag5_1, corner5, paths);

// 20번 코너
        Path diagFrom20 = getDiagFrom20(corner4, diag4_1, diag4_2, center, diag5_2, diag5_1, corner5, paths);

// ─── 3) 중앙(30)에 정확히 멈췄을 때: 30 → 42 → 41 → 0 → 43 ───
        Path centerToStart = getCenterToStart(center, diag6_2, diag6_1, startSpot, finishSpot, paths);

// ─── 4) nextSpot / nextPath 매핑 ───
// 4-1) 외곽 메인 패스: DO 하나당 한 칸씩 진행
        mainPath_move(mainPath);

// 4-2) 코너(5,10,15,20)에서 DO~MO 시 중앙선 진입
        diagPath_move(corner1, diagFrom5, corner2, diagFrom10, corner3, diagFrom15, corner4, diagFrom20);

// 4-3) 중앙(30)에서 DO~MO 시 centerToStart 진입
        centerPath_move(center, centerToStart);

        Board board = new Board(BoardType.HEXAGON, spots, lines, paths, spotPositions);
        return new LayoutResult(board, spotPositions, lines);
    }


}
