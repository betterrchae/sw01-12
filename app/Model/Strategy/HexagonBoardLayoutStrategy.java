package app.Model.Strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import app.Model.Board;
import app.Model.Line;
import app.Model.Path;
import app.Model.Spot;
import app.Model.Enum.BoardType;
import app.View.BoardLayoutStrategy;

public class HexagonBoardLayoutStrategy implements BoardLayoutStrategy {
  @Override
  public Board createBoard() {
    List<Spot> spots = new ArrayList<>();
    List<Line> lines = new ArrayList<>();
    List<Path> paths = new ArrayList<>();

    // 중심점과 화면 크기 설정
    int startX = 300;
    int startY = 700;
    int horizontalLineWidth = 400; // 한변의 길이
    int height = 600;

    int horizontalLineGrid = horizontalLineWidth / 5; // 아랫변 스팟들 간격

    int centerX = startX + horizontalLineWidth / 2;
    int centerY = startY - height / 2;

    // 출발/도착 지점을 포함한 모서리 지점 (더 크게 표시)
    Spot startSpot = new Spot(0, new Point(startX, startY), true,
        true, false);
    Spot corner1 = new Spot(5, new Point(startX + horizontalLineWidth, startY), true,
        false,
        false);
    Spot corner2 = new Spot(10, new Point(startX + horizontalLineWidth * 3 / 2, startY - height / 2), true, false,
        false);
    Spot corner3 = new Spot(15, new Point(startX + horizontalLineWidth, startY -
        height), true, false,
        false);
    Spot corner4 = new Spot(20, new Point(startX, startY - height), true, false,
        false);
    Spot corner5 = new Spot(25, new Point(startX - horizontalLineWidth / 2,
        startY - height / 2), true, false, false);
    Spot center = new Spot(30, new Point(centerX, centerY), true, false, false);

    Spot finishSpot = new Spot(43, new Point(startX - 65, startY), false, false, true);

    spots.add(startSpot);
    spots.add(corner1);
    spots.add(corner2);
    spots.add(corner3);
    spots.add(corner4);
    spots.add(corner5);
    spots.add(center);
    spots.add(finishSpot);

    // 아랫 변
    Spot bottom1 = new Spot(1, new Point(startX + horizontalLineGrid, startY), false, false, false);
    Spot bottom2 = new Spot(2, new Point(startX + horizontalLineGrid * 2, startY), false, false, false);
    Spot bottom3 = new Spot(3, new Point(startX + horizontalLineGrid * 3, startY), false, false, false);
    Spot bottom4 = new Spot(4, new Point(startX + horizontalLineGrid * 4, startY), false, false, false);

    spots.add(bottom1);
    spots.add(bottom2);
    spots.add(bottom3);
    spots.add(bottom4);

    // 오른쪽 + 아래쪽 변
    int heightGrid = height / 2 / 5;
    int widthGrid = horizontalLineWidth / 2 / 5;
    Spot right_bottom1 = new Spot(6,
        new Point(startX + horizontalLineWidth + widthGrid, startY - heightGrid), false, false, false);
    Spot right_bottom2 = new Spot(7,
        new Point(startX + horizontalLineWidth + widthGrid * 2, startY - heightGrid * 2), false, false,
        false);
    Spot right_bottom3 = new Spot(8,
        new Point(startX + horizontalLineWidth + widthGrid * 3, startY - heightGrid * 3), false, false,
        false);
    Spot right_bottom4 = new Spot(9,
        new Point(startX + horizontalLineWidth + widthGrid * 4, startY - heightGrid * 4), false, false,
        false);

    spots.add(right_bottom1);
    spots.add(right_bottom2);
    spots.add(right_bottom3);
    spots.add(right_bottom4);

    // 오른쪽 + 위쪽 변
    Spot right_top1 = new Spot(11,
        new Point(startX + horizontalLineWidth + widthGrid * 4, startY - height / 2 - heightGrid),
        false, false, false);
    Spot right_top2 = new Spot(12,
        new Point(startX + horizontalLineWidth + widthGrid * 3, startY - height / 2 - heightGrid * 2),
        false, false, false);
    Spot right_top3 = new Spot(13,
        new Point(startX + horizontalLineWidth + widthGrid * 2, startY - height / 2 - heightGrid * 3),
        false, false, false);
    Spot right_top4 = new Spot(14,
        new Point(startX + horizontalLineWidth + widthGrid, startY - height / 2 - heightGrid * 4),
        false, false, false);

    spots.add(right_top1);
    spots.add(right_top2);
    spots.add(right_top3);
    spots.add(right_top4);

    // 윗 변
    Spot top1 = new Spot(16, new Point(startX + horizontalLineGrid * 4, startY - height), false, false, false);
    Spot top2 = new Spot(17, new Point(startX + horizontalLineGrid * 3, startY - height), false, false, false);
    Spot top3 = new Spot(18, new Point(startX + horizontalLineGrid * 2, startY - height), false, false, false);
    Spot top4 = new Spot(19, new Point(startX + horizontalLineGrid, startY - height), false, false, false);

    spots.add(top1);
    spots.add(top2);
    spots.add(top3);
    spots.add(top4);

    // // 왼쪽 + 위쪽 변
    Spot left_top1 = new Spot(21, new Point(startX - widthGrid, startY - height / 2 - heightGrid * 4),
        false, false, false);
    Spot left_top2 = new Spot(22, new Point(startX - widthGrid * 2, startY - height / 2 - heightGrid * 3),
        false, false, false);
    Spot left_top3 = new Spot(23, new Point(startX - widthGrid * 3, startY - height / 2 - heightGrid * 2),
        false, false, false);
    Spot left_top4 = new Spot(24, new Point(startX - widthGrid * 4, startY - height / 2 - heightGrid),
        false, false, false);

    spots.add(left_top1);
    spots.add(left_top2);
    spots.add(left_top3);
    spots.add(left_top4);

    // // 왼쪽 + 아래쪽 변
    Spot left_bottom1 = new Spot(26, new Point(startX - widthGrid * 4, startY - heightGrid * 4), false, false, false);
    Spot left_bottom2 = new Spot(27, new Point(startX - widthGrid * 3, startY - heightGrid * 3), false, false, false);
    Spot left_bottom3 = new Spot(28, new Point(startX - widthGrid * 2, startY - heightGrid * 2), false, false, false);
    Spot left_bottom4 = new Spot(29, new Point(startX - widthGrid, startY - heightGrid),
        false, false, false);

    spots.add(left_bottom1);
    spots.add(left_bottom2);
    spots.add(left_bottom3);
    spots.add(left_bottom4);

    // diag1 corner1 -> center
    Spot diag1_1 = new Spot(31, new Point(startX + horizontalLineWidth - 65, startY - 100), false,
        false, false);
    Spot diag1_2 = new Spot(32, new Point(startX + horizontalLineWidth - 130, startY - 200), false,
        false, false);

    // diag2 corner2 -> center
    Spot diag2_1 = new Spot(33, new Point(startX + horizontalLineWidth / 2 + 280, startY - height / 2), false, false,
        false);
    Spot diag2_2 = new Spot(34, new Point(startX + horizontalLineWidth / 2 + 140, startY - height / 2), false, false,
        false);

    // diag3 corner3 -> center
    Spot diag3_1 = new Spot(35, new Point(startX + horizontalLineWidth - 65, startY - height + 100), false, false,
        false);
    Spot diag3_2 = new Spot(36, new Point(startX + horizontalLineWidth - 130, startY - height + 200), false, false,
        false);

    // diag4 corner4 -> center
    Spot diag4_1 = new Spot(37, new Point(startX + 65, startY - height + 100), false, false,
        false);
    Spot diag4_2 = new Spot(38, new Point(startX + 130, startY - height + 200), false, false,
        false);

    // diag5 corner5 -> center
    Spot diag5_1 = new Spot(39, new Point(startX + horizontalLineWidth / 2 - 280, startY - height / 2), false,
        false, false);
    Spot diag5_2 = new Spot(40, new Point(startX + horizontalLineWidth / 2 - 140, startY - height / 2), false,
        false, false);

    // diag6 start -> center
    Spot diag6_1 = new Spot(41, new Point(startX + 65, startY - 100), false, false, false);
    Spot diag6_2 = new Spot(42, new Point(startX + 130, startY - 200), false, false, false);

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

    // 윗변
    lines.add(new Line(corner3, top1));
    lines.add(new Line(top1, top2));
    lines.add(new Line(top2, top3));
    lines.add(new Line(top3, top4));
    lines.add(new Line(top4, corner4));

    // 왼쪽 위 변
    lines.add(new Line(corner4, left_top1));
    lines.add(new Line(left_top1, left_top2));
    lines.add(new Line(left_top2, left_top3));
    lines.add(new Line(left_top3, left_top4));
    lines.add(new Line(left_top4, corner5));

    // 왼쪽 아래 변
    lines.add(new Line(corner5, left_bottom1));
    lines.add(new Line(left_bottom1, left_bottom2));
    lines.add(new Line(left_bottom2, left_bottom3));
    lines.add(new Line(left_bottom3, left_bottom4));
    lines.add(new Line(left_bottom4, startSpot));

    // 대각선 선 연결
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

    // 시작점에서 도착점 연결
    lines.add(new Line(startSpot, finishSpot));

    return new Board(BoardType.PENTAGON, spots, lines, paths);
  }
}
