package app.Model.Strategy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import app.Model.Enum.YutResult;
import app.Model.Board;
import app.Model.Line;
import app.Model.Path;
import app.Model.Spot;
import app.View.BoardLayoutStrategy;

public class SquareBoardLayoutStrategy implements BoardLayoutStrategy {
        @Override
        public Board createBoard() {
                List<Spot> spots = new ArrayList<>();
                List<Line> lines = new ArrayList<>();
                List<Path> paths = new ArrayList<>();

                // 중심점과 화면 크기 설정
                int centerX = 300;
                int centerY = 300;
                int boardSize = 400; // 보드 전체 크기
                int gridSize = boardSize / 5; // 격자 간격

                // 출발/도착 지점을 포함한 모서리 지점 (더 크게 표시)
                Spot startSpot = new Spot(0, new Point(centerX - boardSize / 2, centerY + boardSize / 2), true, true,
                                false);
                Spot corner1 = new Spot(5, new Point(centerX - boardSize / 2, centerY - boardSize / 2), true, false,
                                false);
                Spot corner2 = new Spot(10, new Point(centerX + boardSize / 2, centerY - boardSize / 2), true, false,
                                false);
                Spot corner3 = new Spot(15, new Point(centerX + boardSize / 2, centerY + boardSize / 2), true, false,
                                false);

                // 중앙 지점
                Spot center = new Spot(20, new Point(centerX, centerY), true, false, false);

                // 도착 지점 (출발 지점 옆)
                // Spot finishSpot = new Spot(29, new Point(centerX - boardSize / 2 + gridSize,
                // centerY + boardSize / 2), false,
                // false, true);
                // Spot finishSpot = new Spot(29, new Point(centerX - boardSize / 2, centerY +
                // boardSize / 2), true, false, true);
                Spot finishSpot = new Spot(29,
                                new Point(centerX - boardSize / 2 - gridSize / 2,
                                                centerY + boardSize / 2 + gridSize / 2),
                                false, false, true);

                // 모서리 지점 추가
                spots.add(startSpot);
                spots.add(corner1);
                spots.add(corner2);
                spots.add(corner3);
                spots.add(center);
                spots.add(finishSpot);

                // 왼쪽 변 (시작점에서 위쪽 모서리까지의 경로)
                Spot left1 = new Spot(1, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - gridSize), false,
                                false,
                                false);
                Spot left2 = new Spot(2, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 2 * gridSize),
                                false,
                                false, false);
                Spot left3 = new Spot(3, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 3 * gridSize),
                                false,
                                false, false);
                Spot left4 = new Spot(4, new Point(centerX - boardSize / 2, centerY + boardSize / 2 - 4 * gridSize),
                                false,
                                false, false);

                // 위쪽 변 (왼쪽 모서리에서 오른쪽 모서리까지의 경로)
                Spot top1 = new Spot(6, new Point(centerX - boardSize / 2 + gridSize, centerY - boardSize / 2), false,
                                false,
                                false);
                Spot top2 = new Spot(7, new Point(centerX - boardSize / 2 + 2 * gridSize, centerY - boardSize / 2),
                                false,
                                false, false);
                Spot top3 = new Spot(8, new Point(centerX - boardSize / 2 + 3 * gridSize, centerY - boardSize / 2),
                                false,
                                false, false);
                Spot top4 = new Spot(9, new Point(centerX - boardSize / 2 + 4 * gridSize, centerY - boardSize / 2),
                                false,
                                false, false);

                // 오른쪽 변 (오른쪽 위 모서리에서 오른쪽 아래 모서리까지의 경로)
                Spot right1 = new Spot(11, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + gridSize),
                                false, false,
                                false);
                Spot right2 = new Spot(12, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 2 * gridSize),
                                false,
                                false, false);
                Spot right3 = new Spot(13, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 3 * gridSize),
                                false,
                                false, false);
                Spot right4 = new Spot(14, new Point(centerX + boardSize / 2, centerY - boardSize / 2 + 4 * gridSize),
                                false,
                                false, false);

                // 아래쪽 변 (오른쪽 아래 모서리에서 시작점까지의 경로)
                Spot bottom1 = new Spot(16, new Point(centerX + boardSize / 2 - gridSize, centerY + boardSize / 2),
                                false,
                                false, false);
                Spot bottom2 = new Spot(17, new Point(centerX + boardSize / 2 - 2 * gridSize, centerY + boardSize / 2),
                                false,
                                false, false);
                Spot bottom3 = new Spot(18, new Point(centerX + boardSize / 2 - 3 * gridSize, centerY + boardSize / 2),
                                false,
                                false, false);
                Spot bottom4 = new Spot(19, new Point(centerX + boardSize / 2 - 4 * gridSize, centerY + boardSize / 2),
                                false,
                                false, false);

                // 왼쪽 아래 대각선 (시작점에서 중앙까지의 경로)
                Spot diag1_1 = new Spot(21,
                                new Point(centerX - boardSize / 2 + gridSize, centerY + boardSize / 2 - gridSize),
                                false, false, false);
                Spot diag1_2 = new Spot(22,
                                new Point(centerX - boardSize / 2 + 2 * gridSize,
                                                centerY + boardSize / 2 - 2 * gridSize),
                                false, false,
                                false);

                // 왼쪽 위 대각선 (왼쪽 위 모서리에서 중앙까지의 경로)
                Spot diag2_1 = new Spot(23,
                                new Point(centerX - boardSize / 2 + gridSize, centerY - boardSize / 2 + gridSize),
                                false, false, false);
                Spot diag2_2 = new Spot(24,
                                new Point(centerX - boardSize / 2 + 2 * gridSize,
                                                centerY - boardSize / 2 + 2 * gridSize),
                                false, false,
                                false);

                // 오른쪽 위 대각선 (오른쪽 위 모서리에서 중앙까지의 경로)
                Spot diag3_1 = new Spot(25,
                                new Point(centerX + boardSize / 2 - gridSize, centerY - boardSize / 2 + gridSize),
                                false, false, false);
                Spot diag3_2 = new Spot(26,
                                new Point(centerX + boardSize / 2 - 2 * gridSize,
                                                centerY - boardSize / 2 + 2 * gridSize),
                                false, false,
                                false);

                // 오른쪽 아래 대각선 (오른쪽 아래 모서리에서 중앙까지의 경로)
                Spot diag4_1 = new Spot(27,
                                new Point(centerX + boardSize / 2 - gridSize, centerY + boardSize / 2 - gridSize),
                                false, false, false);
                Spot diag4_2 = new Spot(28,
                                new Point(centerX + boardSize / 2 - 2 * gridSize,
                                                centerY + boardSize / 2 - 2 * gridSize),
                                false, false,
                                false);

                // 모든 일반 지점 추가
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

                // 외곽 선 연결
                // 왼쪽 변
                lines.add(new Line(startSpot, left1));
                lines.add(new Line(left1, left2));
                lines.add(new Line(left2, left3));
                lines.add(new Line(left3, left4));
                lines.add(new Line(left4, corner1));

                // 위쪽 변
                lines.add(new Line(corner1, top1));
                lines.add(new Line(top1, top2));
                lines.add(new Line(top2, top3));
                lines.add(new Line(top3, top4));
                lines.add(new Line(top4, corner2));

                // 오른쪽 변
                lines.add(new Line(corner2, right1));
                lines.add(new Line(right1, right2));
                lines.add(new Line(right2, right3));
                lines.add(new Line(right3, right4));
                lines.add(new Line(right4, corner3));

                // 아래쪽 변
                lines.add(new Line(corner3, bottom1));
                lines.add(new Line(bottom1, bottom2));
                lines.add(new Line(bottom2, bottom3));
                lines.add(new Line(bottom3, bottom4));
                lines.add(new Line(bottom4, startSpot));

                // 대각선 연결
                // 왼쪽 아래 대각선
                lines.add(new Line(startSpot, diag1_1));
                lines.add(new Line(diag1_1, diag1_2));
                lines.add(new Line(diag1_2, center));

                // 왼쪽 위 대각선
                lines.add(new Line(corner1, diag2_1));
                lines.add(new Line(diag2_1, diag2_2));
                lines.add(new Line(diag2_2, center));

                // 오른쪽 위 대각선
                lines.add(new Line(corner2, diag3_1));
                lines.add(new Line(diag3_1, diag3_2));
                lines.add(new Line(diag3_2, center));

                // 오른쪽 아래 대각선
                lines.add(new Line(corner3, diag4_1));
                lines.add(new Line(diag4_1, diag4_2));
                lines.add(new Line(diag4_2, center));

                // 시작점과 도착점 연결 (시작점에서 한 번에 도착점으로)
                lines.add(new Line(startSpot, finishSpot));

                // // 중앙에서 도착점 연결
                // lines.add(new Line(center, finishSpot));

                // 경로 설정
                // 기본 외곽 경로
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
                paths.add(mainPath);

                // 대각선 경로 - 왼쪽 아래에서 중앙
                Path diagPath1 = new Path("diag1", true);
                diagPath1.addSpot(startSpot);
                diagPath1.addSpot(diag1_1);
                diagPath1.addSpot(diag1_2);
                diagPath1.addSpot(center);
                paths.add(diagPath1);

                // 대각선 경로 - 왼쪽 위에서 중앙
                Path diagPath2 = new Path("diag2", true);
                diagPath2.addSpot(corner1);
                diagPath2.addSpot(diag2_1);
                diagPath2.addSpot(diag2_2);
                diagPath2.addSpot(center);
                paths.add(diagPath2);

                // 대각선 경로 - 오른쪽 위에서 중앙
                Path diagPath3 = new Path("diag3", true);
                diagPath3.addSpot(corner2);
                diagPath3.addSpot(diag3_1);
                diagPath3.addSpot(diag3_2);
                diagPath3.addSpot(center);
                paths.add(diagPath3);

                // 대각선 경로 - 오른쪽 아래에서 중앙
                Path diagPath4 = new Path("diag4", true);
                diagPath4.addSpot(corner3);
                diagPath4.addSpot(diag4_1);
                diagPath4.addSpot(diag4_2);
                diagPath4.addSpot(center);
                paths.add(diagPath4);

                // 도착 경로 - 중앙에서 도착점
                Path finishPath = new Path("finish", false);
                finishPath.addSpot(center);
                finishPath.addSpot(finishSpot);
                paths.add(finishPath);

                // 도착 경로 - 시작점에서 도착점 (직접 이동)
                Path directFinishPath = new Path("directFinish", false);
                directFinishPath.addSpot(startSpot);
                directFinishPath.addSpot(finishSpot);
                paths.add(directFinishPath);

                // 다음 칸 연결 설정 (선 그리기보다 실제 이동 가능 경로 설정)
                // 기본 경로 연결
                for (int i = 0; i < mainPath.getSpots().size() - 1; i++) {
                        Spot current = mainPath.getSpots().get(i);
                        Spot next = mainPath.getSpots().get(i + 1);
                        current.addNextSpot(YutResult.DO, next);
                }

                // 모서리에서 대각선 경로 연결 (일반 말 이동으로 들어갈 수 있는 경로)
                startSpot.addNextPath(YutResult.DO, diagPath1);
                corner1.addNextPath(YutResult.DO, diagPath2);
                corner2.addNextPath(YutResult.DO, diagPath3);
                corner3.addNextPath(YutResult.DO, diagPath4);

                // 중앙에서 도착점 연결
                center.addNextPath(YutResult.DO, finishPath);

                // 시작점에서 도착점 직접 연결
                startSpot.addNextPath(YutResult.MO, directFinishPath); // 모가 나오면 바로 도착

                return new Board(spots, lines, paths);
        }
}