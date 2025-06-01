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

public class SquareBoardLayoutStrategy implements BoardLayoutStrategy {
    private static void extracted(List<Spot> spots, Spot startSpot, Spot corner1, Spot corner2, Spot corner3, Spot centerSpot, Spot finishSpot, Spot left1, Spot left2, Spot left3, Spot left4, Spot top1, Spot top2, Spot top3, Spot top4, Spot right1, Spot right2, Spot right3, Spot right4, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot diag1_1, Spot diag1_2, Spot diag2_1, Spot diag2_2, Spot diag3_1, Spot diag3_2, Spot diag4_1, Spot diag4_2) {
        spots.add(startSpot);
        spots.add(corner1);
        spots.add(corner2);
        spots.add(corner3);
        spots.add(centerSpot);
        spots.add(finishSpot);
        spots.add(left1);
        spots.add(left2);
        spots.add(left3);
        spots.add(left4);
        spots.add(top1);
        spots.add(top2);
        spots.add(top3);
        spots.add(top4);
        spots.add(right1);
        spots.add(right2);
        spots.add(right3);
        spots.add(right4);
        spots.add(bottom1);
        spots.add(bottom2);
        spots.add(bottom3);
        spots.add(bottom4);
        spots.add(diag1_1);
        spots.add(diag1_2);
        spots.add(diag2_1);
        spots.add(diag2_2);
        spots.add(diag3_1);
        spots.add(diag3_2);
        spots.add(diag4_1);
        spots.add(diag4_2);
    }

    private static void extracted_left(List<Line> lines, Spot startSpot, Spot left1, Spot left2, Spot left3, Spot left4, Spot corner1) {
        lines.add(new Line(startSpot, left1));
        lines.add(new Line(left1, left2));
        lines.add(new Line(left2, left3));
        lines.add(new Line(left3, left4));
        lines.add(new Line(left4, corner1));
    }

    private static void extracted_top(List<Line> lines, Spot corner1, Spot top1, Spot top2, Spot top3, Spot top4, Spot corner2) {
        lines.add(new Line(corner1, top1));
        lines.add(new Line(top1, top2));
        lines.add(new Line(top2, top3));
        lines.add(new Line(top3, top4));
        lines.add(new Line(top4, corner2));
    }

    private static void extracted_right(List<Line> lines, Spot corner2, Spot right1, Spot right2, Spot right3, Spot right4, Spot corner3) {
        lines.add(new Line(corner2, right1));
        lines.add(new Line(right1, right2));
        lines.add(new Line(right2, right3));
        lines.add(new Line(right3, right4));
        lines.add(new Line(right4, corner3));
    }

    private static void extracted_bottom(List<Line> lines, Spot corner3, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot startSpot) {
        lines.add(new Line(corner3, bottom1));
        lines.add(new Line(bottom1, bottom2));
        lines.add(new Line(bottom2, bottom3));
        lines.add(new Line(bottom3, bottom4));
        lines.add(new Line(bottom4, startSpot));
    }

    private static void extracted_diag_1(List<Line> lines, Spot startSpot, Spot diag1_1, Spot diag1_2, Spot centerSpot) {
        lines.add(new Line(startSpot, diag1_1));
        lines.add(new Line(diag1_1, diag1_2));
        lines.add(new Line(diag1_2, centerSpot));
    }

    private static void extracted_diag_2(List<Line> lines, Spot corner1, Spot diag2_1, Spot diag2_2, Spot centerSpot) {
        lines.add(new Line(corner1, diag2_1));
        lines.add(new Line(diag2_1, diag2_2));
        lines.add(new Line(diag2_2, centerSpot));
    }

    private static void extracted_diag_3(List<Line> lines, Spot corner2, Spot diag3_1, Spot diag3_2, Spot centerSpot) {
        lines.add(new Line(corner2, diag3_1));
        lines.add(new Line(diag3_1, diag3_2));
        lines.add(new Line(diag3_2, centerSpot));
    }

    private static void extracted_diag_4(List<Line> lines, Spot corner3, Spot diag4_1, Spot diag4_2, Spot centerSpot) {
        lines.add(new Line(corner3, diag4_1));
        lines.add(new Line(diag4_1, diag4_2));
        lines.add(new Line(diag4_2, centerSpot));
    }

    private static Path getCenterToStart(Spot centerSpot, Spot diag1_2, Spot diag1_1, Spot startSpot, Spot finishSpot, List<Path> paths) {
        Path centerToStart = new Path("centerToStart", true);
        centerToStart.addSpot(centerSpot);
        centerToStart.addSpot(diag1_2);
        centerToStart.addSpot(diag1_1);
        centerToStart.addSpot(startSpot);
        centerToStart.addSpot(finishSpot);
        paths.add(centerToStart);
        return centerToStart;
    }

    private static Path getDiagTopRightToBottomLeft(Spot corner2, Spot diag3_1, Spot diag3_2, Spot centerSpot, Spot diag1_2, Spot diag1_1, Spot startSpot, Spot finishSpot, List<Path> paths) {
        Path diagTopRightToBottomLeft = new Path("diagTopRightToBottomLeft", true);
        diagTopRightToBottomLeft.addSpot(corner2);
        diagTopRightToBottomLeft.addSpot(diag3_1);
        diagTopRightToBottomLeft.addSpot(diag3_2);
        diagTopRightToBottomLeft.addSpot(centerSpot);
        diagTopRightToBottomLeft.addSpot(diag1_2);
        diagTopRightToBottomLeft.addSpot(diag1_1);
        diagTopRightToBottomLeft.addSpot(startSpot);
        diagTopRightToBottomLeft.addSpot(finishSpot);

        paths.add(diagTopRightToBottomLeft);
        return diagTopRightToBottomLeft;
    }

    private static Path getDiagTopLeftToBotRight(Spot corner1, Spot diag2_1, Spot diag2_2, Spot centerSpot, Spot diag4_1, Spot diag4_2, Spot corner3, List<Path> paths) {
        Path diagTopLeftToBotRight = new Path("diagTopLeftToBotRight", true);
        diagTopLeftToBotRight.addSpot(corner1);
        diagTopLeftToBotRight.addSpot(diag2_1);
        diagTopLeftToBotRight.addSpot(diag2_2);
        diagTopLeftToBotRight.addSpot(centerSpot);
        diagTopLeftToBotRight.addSpot(diag4_1);
        diagTopLeftToBotRight.addSpot(diag4_2);
        diagTopLeftToBotRight.addSpot(corner3);
        paths.add(diagTopLeftToBotRight);
        return diagTopLeftToBotRight;
    }

    private static Path getmainPath(Spot startSpot, Spot left1, Spot left2, Spot left3, Spot left4, Spot corner1, Spot top1, Spot top2, Spot top3, Spot top4, Spot corner2, Spot right1, Spot right2, Spot right3, Spot right4, Spot corner3, Spot bottom1, Spot bottom2, Spot bottom3, Spot bottom4, Spot finishSpot) {
        Path mainPath = new Path("main", false);
        mainPath.addSpot(startSpot);
        mainPath.addSpot(left1);
        mainPath.addSpot(left2);
        mainPath.addSpot(left3);
        mainPath.addSpot(left4);
        mainPath.addSpot(corner1);
        mainPath.addSpot(top1);
        mainPath.addSpot(top2);
        mainPath.addSpot(top3);
        mainPath.addSpot(top4);
        mainPath.addSpot(corner2);
        mainPath.addSpot(right1);
        mainPath.addSpot(right2);
        mainPath.addSpot(right3);
        mainPath.addSpot(right4);
        mainPath.addSpot(corner3);
        mainPath.addSpot(bottom1);
        mainPath.addSpot(bottom2);
        mainPath.addSpot(bottom3);
        mainPath.addSpot(bottom4);
        mainPath.addSpot(startSpot);
        // 한 바퀴 돌면 도착점으로
        mainPath.addSpot(finishSpot);
        return mainPath;
    }

    private static void diagPath_move(Spot corner1, Path diagTopLeftToBotRight, Spot corner2, Path diagTopRightToBottomLeft, Spot centerSpot, Path centerToStart) {
        for (YutResult r : YutResult.values()) {
            if (r != YutResult.BACKDO) {
                corner1.addNextPath(r, diagTopLeftToBotRight);
                corner2.addNextPath(r, diagTopRightToBottomLeft);
                centerSpot.addNextPath(r, centerToStart);
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

    private static void extracted_directFinish(Spot finishSpot, List<Path> paths) {
        Path directFinish = new Path("directFinish", false);
        directFinish.addSpot(finishSpot);
        paths.add(directFinish);
    }

    @Override
    public LayoutResult createLayout() {
        List<Spot> spots = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        Map<Spot, Point> spotPositions = new HashMap<>();

        int centerX = 300, centerY = 300;
        int boardSize = 400, grid = boardSize / 5;

        // 1) 핵심 Spot 생성 및 좌표 매핑
        Spot start = new Spot(0, true, true, false);
        spotPositions.put(start, new Point(centerX - boardSize / 2, centerY + boardSize / 2));

        Spot corner1 = new Spot(5, true, false, false);
        spotPositions.put(corner1, new Point(centerX - boardSize / 2, centerY - boardSize / 2));

        Spot corner2 = new Spot(10, true, false, false);
        spotPositions.put(corner2, new Point(centerX + boardSize / 2, centerY - boardSize / 2));

        Spot corner3 = new Spot(15, true, false, false);
        spotPositions.put(corner3, new Point(centerX + boardSize / 2, centerY + boardSize / 2));

        Spot center = new Spot(20, true, false, false);
        spotPositions.put(center, new Point(centerX, centerY));

        Spot finish = new Spot(29, false, false, true);
        spotPositions.put(finish, new Point(centerX - boardSize / 2 - grid / 2, centerY + boardSize / 2 + grid / 2));

        // 2) 사이드 Spot 생성
        Spot left1 = new Spot(1, false, false, false);
        spotPositions.put(left1, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - grid));
        Spot left2 = new Spot(2, false, false, false);
        spotPositions.put(left2, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 2 * grid));
        Spot left3 = new Spot(3, false, false, false);
        spotPositions.put(left3, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 3 * grid));
        Spot left4 = new Spot(4, false, false, false);
        spotPositions.put(left4, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 4 * grid));

        Spot top1 = new Spot(6, false, false, false);
        spotPositions.put(top1, new Point(centerX - boardSize / 2 + grid, centerY - boardSize / 2));
        Spot top2 = new Spot(7, false, false, false);
        spotPositions.put(top2, new Point(centerX - boardSize / 2 + 2 * grid, centerY - boardSize / 2));
        Spot top3 = new Spot(8, false, false, false);
        spotPositions.put(top3, new Point(centerX - boardSize / 2 + 3 * grid, centerY - boardSize / 2));
        Spot top4 = new Spot(9, false, false, false);
        spotPositions.put(top4, new Point(centerX - boardSize / 2 + 4 * grid, centerY - boardSize / 2));

        Spot right1 = new Spot(11, false, false, false);
        spotPositions.put(right1, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + grid));
        Spot right2 = new Spot(12, false, false, false);
        spotPositions.put(right2, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 2 * grid));
        Spot right3 = new Spot(13, false, false, false);
        spotPositions.put(right3, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 3 * grid));
        Spot right4 = new Spot(14, false, false, false);
        spotPositions.put(right4, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 4 * grid));

        Spot bottom1 = new Spot(16, false, false, false);
        spotPositions.put(bottom1, new Point(centerX + boardSize / 2 - grid, centerY + boardSize / 2));
        Spot bottom2 = new Spot(17, false, false, false);
        spotPositions.put(bottom2, new Point(centerX + boardSize / 2 - 2 * grid, centerY + boardSize / 2));
        Spot bottom3 = new Spot(18, false, false, false);
        spotPositions.put(bottom3, new Point(centerX + boardSize / 2 - 3 * grid, centerY + boardSize / 2));
        Spot bottom4 = new Spot(19, false, false, false);
        spotPositions.put(bottom4, new Point(centerX + boardSize / 2 - 4 * grid, centerY + boardSize / 2));

        // 3) 대각선 Spot 생성
        Spot diag1 = new Spot(21, false, false, false);
        spotPositions.put(diag1, new Point(centerX - boardSize / 2 + grid, centerY + boardSize / 2 - grid));
        Spot diag2 = new Spot(22, false, false, false);
        spotPositions.put(diag2, new Point(centerX - boardSize / 2 + 2 * grid, centerY + boardSize / 2 - 2 * grid));
        Spot diag3 = new Spot(23, false, false, false);
        spotPositions.put(diag3, new Point(centerX - boardSize / 2 + grid, centerY - boardSize / 2 + grid));
        Spot diag4 = new Spot(24, false, false, false);
        spotPositions.put(diag4, new Point(centerX - boardSize / 2 + 2 * grid, centerY - boardSize / 2 + 2 * grid));
        Spot diag5 = new Spot(25, false, false, false);
        spotPositions.put(diag5, new Point(centerX + boardSize / 2 - grid, centerY - boardSize / 2 + grid));
        Spot diag6 = new Spot(26, false, false, false);
        spotPositions.put(diag6, new Point(centerX + boardSize / 2 - 2 * grid, centerY - boardSize / 2 + 2 * grid));
        Spot diag7 = new Spot(27, false, false, false);
        spotPositions.put(diag7, new Point(centerX + boardSize / 2 - grid, centerY + boardSize / 2 - grid));
        Spot diag8 = new Spot(28, false, false, false);
        spotPositions.put(diag8, new Point(centerX + boardSize / 2 - 2 * grid, centerY + boardSize / 2 - 2 * grid));

        // 4) Spot 목록에 추가
        extracted(spots, start, corner1, corner2, corner3, center, finish, left1, left2, left3, left4, top1, top2, top3, top4, right1, right2, right3, right4, bottom1, bottom2, bottom3, bottom4, diag1, diag2, diag3, diag4, diag5, diag6, diag7, diag8);

        // 5) 외곽선 연결
        extracted_left(lines, start, left1, left2, left3, left4, corner1);
        extracted_top(lines, corner1, top1, top2, top3, top4, corner2);
        extracted_right(lines, corner2, right1, right2, right3, right4, corner3);
        extracted_bottom(lines, corner3, bottom1, bottom2, bottom3, bottom4, start);

        // 6) 대각선 연결
        extracted_diag_1(lines, start, diag1, diag2, center);
        extracted_diag_2(lines, corner1, diag3, diag4, center);
        extracted_diag_3(lines, corner2, diag5, diag6, center);
        extracted_diag_4(lines, corner3, diag7, diag8, center);

        // 7) 바로 연결
        lines.add(new Line(start, finish));

        // 8) Path 설정
        Path main = getmainPath(start, left1, left2, left3, left4, corner1, top1, top2, top3, top4, corner2, right1, right2, right3, right4, corner3, bottom1, bottom2, bottom3, bottom4, finish);
        paths.add(main);
        Path tlbr = getDiagTopLeftToBotRight(corner1, diag3, diag4, center, diag7, diag8, corner3, paths);
        Path trbl = getDiagTopRightToBottomLeft(corner2, diag5, diag6, center, diag2, diag1, start, finish, paths);
        Path c2s = getCenterToStart(center, diag2, diag1, start, finish, paths);
        extracted_directFinish(finish, paths);

        mainPath_move(main);
        diagPath_move(corner1, tlbr, corner2, trbl, center, c2s);

        // 9) Board 및 LayoutResult 생성
        Board board = new Board(BoardType.SQUARE, spots, lines, paths, spotPositions);
        return new LayoutResult(board, spotPositions, lines);
    }
}
