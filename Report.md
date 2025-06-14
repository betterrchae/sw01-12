### 보고서 역할분담
1. 프로젝트 개요 - 은채
2. 유스케이스 모델 - 민혁
3. 설계 및 구현 리포트 (단계별로, 발전 과정 기술) - 민혁
4. UI를 교체했을 때 수정되어야 하는 부분들에 대한 설명을 보고서에 반드시 작성 - 은채
5. 테스트 리포트 - 휘민
6. GitHub 프로젝트 리포트 - 휘민

# 프로젝트 개요
 이 프로젝트는 전통 윷놀이 게임을 객체 지향 분석 및 설계 기법(OOAD)을 활용해 소프트웨어로 개발하는 것을 목표로 한다. 사용자는 말과 사용자 수를 선택할 수 있으며, 커스터마이징 가능한 윷놀이 판(오각형, 육각형)과 윷 던지기 기능(지정 혹은 랜덤), 말 선택 및 이동, 업기, 잡기, 게임 종료 또는 재시작의 기능을 지원한다. 또한, MVC 아키텍처 패턴을 사용하여 UI를 분리함으로써 두 가지 이상의 UIToolKit을 사용하여 구현했을 때 코드 수정을 최소화해야 한다. 마지막으로 JUnit5으로 모델 테스트를 수행한다.

![swing_square](report_img/swing_square.png)
![swing_pentagon](report_img/swing_pentagon.png)
![swing_hexagon](report_img/swing_hexagon.png)

![javafx_square](report_img/javafx_square.png)
![javafx_pentagon](report_img/javafx_pentagon.png)
![javafx_hexagon](report_img/javafx_hexagon.png)

# UseCase Model
## UseCase Text

**Scope**
Yut Game System

**Level**
User Goal Level

**\<UseCase List\>**
1. 게임 실행
    
    **Primary Actor**
    
    Player
    
    **Stakeholders and Interests list**
    
    Player: 원하는 설정값으로 윷 게임 진행
    
    System: 플레이어가 윷 게임을 진행할 수 있도록 게임 실행을 보장
    
    **Success Guarantee**
    
    1. 윷놀이 게임을 진행한다.
    
    **Main Success Scenario**
    
    1. UI 프레임워크 타입을 설정한다.
    2. 보드 유형, 플레이어 수(2~4)와 말 수(2~5)를 선택한다.
    3. 게임 실행 버튼을 누른다.
    
    **Extensions**
    
    1. 설정값을 설정하지 않은 경우: 게임이 실행되지 않는다.
    
2. 윷 던지기
    
    **Primary Actor**
    
    Player
    
    **Stakeholders and Interests list**
    
    Player: 윷 결과를 확인하고 게임을 진행
    
    System: 올바른 윷 결과 생성 및 플레이어에게 전달을 보장
    
    **Precondition**
    
    1. 게임이 진행 중이어야 한다.
    
    **Success Guarantee**
    
    1. 플레이어가 윷 결과를 사용할 수 있도록 윷 결과가 기록된다.
    2. 윷, 모인 경우 윷 던지기 기회를 한번 더 제공한다.
    
    **Main Success Scenario**
    
    1. 지정 윷던지기, 랜덤 윷던지기 중 하나를 선택한다.
    2. 윷던지기 버튼을 누른다.
    3. 윷 결과가 표시된다.
    4. 윷 결과가 저장된다.
    
    **Extensions**
    
    1. 윷 던지기 기회를 모두 사용한 경우: 말 선택 및 이동하는 단계로 넘어간다.
    2. 보드에 나온 말이 없는 상태로 빽도가 나온 경우: 턴이 즉시 종료되고, 턴이 넘어간다.
    
3. 말 이동
    
    **Primary Actor**
    
    Player
    
    **Stakeholders and Interests list**
    
    Player: 윷 결과에 따라 말을 움직이고 모든 말을 완주
    
    System: 규칙에 맞게 플레이어가 원하는 대로 이동을 처리하고 게임 상태를 정확히 반영
    
    **Precondition**
    
    1. 윷을 던져 결과가 하나 이상 존재해야 한다.
    
    **Success Guarantee**
    
    1. 이동이 완료되면 사용한 윷 결과를 제거한다.
    
    **Main Success Scenario**
    
    1. 현재 턴의 플레이어가 말을 선택한다.
    2. 이동에 사용할 윷 결과를 선택한다.
    3. 말이 지정된 칸으로 이동한다.
    
    **Extensions**
    
    1. 해당 칸에 같은 팀 말이 있는 경우: 업기를 진행한다.
    2. 해당 칸에 다른 팀 말이 있는 경우: 상대 말을 잡고 윷 던지기 기회를 한번 더 제공한다.
    
4. 승자 결정
    
    **Primary Actor**
    
    System
    
    **Stakeholders and Interests list**
    
    Player: 모든 말을 모두 완주시켜 승리
    
    System: 승자를 결정
    
    **Precondition**
    
    1. 한 플레이어가 자신의 모든 말을 완주한다.
    
    **Success Guarantee**
    
    1. 게임 재시작 또는 게임 종료 중 선택할 수 있도록 다이얼로그를 표시한다.
    
    **Main Success Scenario**
    
    1. 플레이어가 말 이동한 후 모든 말이 완주했는지 체크한다.
    2. 모든 말이 완주했다면 해당 플레이어를 승리자로 판단하고 게임을 종료한다.
    3. 다이얼로그를 통해 승리자를 표시한다.
    
    **Extensions**
    
    1. 게임 재시작을 누른 경우: 새로운 게임이 시작되고, 게임 설정 화면이 나타난다.
    2. 게임 종료를 누른 경우: UI가 종료된다.
5. 턴 전환
    
    **Primary Actor**
    
    System
    
    **Stakeholders and Interests list**
    
    Player: 자신의 차례가 제대로 돌아오길 원함
    
    System: 규칙에 따라 플레이어 턴을 정확히 전환
    
    **Precondition**
    
    1. 현재 플레이어의 윷 던지기 횟수가 남아있지 않다.
    2. 현재 플레이어의 윷 결과 사용이 모두 완료되었다.
    
    **Success Guarantee**
    
    1. 다음 순번의 플레이어가 현재 플레이어로 설정된다.
    
    **Main Success Scenario**
    
    1. 현재 플레이어의 윷던지기 횟수가 모두 사용 됐는지 확인한다.
    2. 사용하지 않은 윷 결과가 있는지 확인한다.
    3. 조건을 만족하는 경우, 다음 플레이어에게 턴을 넘긴다.
    4. 다이얼로그를 통해 턴이 넘어갔음을 알린다.
    
6. 게임 재시작
    
    **Primary Actor**
    
    Player
    
    **Stakeholders and Interests list**
    
    Player: 새로운 게임을 진행
    
    System: 기존 게임을 종료하고 새로운 게임 실행을 보장
    
    **Precondition**
    
    1. 승리자가 존재한다.
    
    **Main Success Scenario**
    
    1. 승리자가 나오고 게임이 끝난 상태에서 게임 재시작 버튼을 누른다.
    2. 기존 게임을 초기화하고 새로운 게임의 설정창을 보여준다.
    
    **Extensions**
    
    1. 새로운 게임의 설정을 완료한 경우: 새로운 게임이 시작된다.
    
7. 게임 종료
    
    **Primary Actor**
    
    Player
    
    **Stakeholders and Interests list**
    
    Player: 게임을 종료
    
    System: 기존 게임을 종료하고 UI를 종료
    
    **Precondition**
    
    1. 승리자가 존재한다.
    
    **Main Success Scenario**
    
    1. 승리자가 나오고 게임이 끝난 상태에서 게임 종료 버튼을 누른다.
    2. UI가 닫히며 프로그램이 종료된다.

## UseCase Diagram
![UseCaseDiagram](report_img/usecase_diagram.png)

# 설계 및 구현 리포트

## 주요 구현 목표
1. MVC 구조 적용
2. 여러 버전의 UI 프레임워크를 적용하기 위한 구현
    1. JavaSwing UI 구현
    2. JavaFX UI 구현
    3. 각 프레임워크 적용 시 코드 수정 최소화
3. 게임 설정 (보드 종류, 플레이어 수, 말 수) 구현
4. 게임 진행 상태 및 턴 관리 구현
    1. 윷, 모의 경우 윷 한번 더 던지기
    2. 말을 잡은 경우 윷 한번 더 던지기
    3. 나온 윷을 모두 사용해 말을 이동한 경우, 다음 플레이어에게 턴 넘기기
5. 랜덤 윷던지기와 지정 윷던지기, 2가지 버전의 윷던지기 구현
6. 보드 레이아웃 및 이동 전략 구현
    1. 사각형 보드 레이아웃 구현
    2. 오각형 보드 레이아웃 구현
    3. 육각형 보드 레이아웃 구현
7. 말 이동 전략 구현
    1. 말 업기, 말 잡기 구현
    2. 각 꼭짓점 및 중앙점에서 지름길로 이동하는 전략 구현
    3. 나온 윷에 해당하는 이동 구현

## 시스템 구성 및 설계 구조
### MVC 구조

#### 주요 Controller Class

- GameController

#### 주요 Model Class
**Object**

- Game
- Board
- Line
- Path
- Spot
- Yut
- Horse
    - Horse
    - HorseGroup
- Player

**Event**

- GameEvent
- GameEventListener(인터페이스)
- GameEventManager

**Strategy**

- BoardFactory
- BoardLayoutStrategy (인터페이스)
    - HexagonBoardLayoutStrategy
    - PentagonBoardLayoutStrategy
    - SquareBoardLayoutStrategy
- YutThrowStartegry (인터페이스)
    - RandomYutThrowStartegry
    - SpecificYutThrowStartegry

#### 주요 View Class

- GameView (인터페이스)
    - SwingGameView
    - JavaFXGameView
- GameViewFactory

### 옵저버 패턴

모델과 뷰 간 데이터 처리, 전달 등을 구현하기 위해, 옵저버 패턴을 적용하여 이벤트 기반으로 구현하였다.

이벤트가 발생하여 GameEventListener에 전달되면 이벤트를 수신하고 onGameEvent 메소드를 호출하여 처리한다. (Observer)

GameEventManager는 등록된 이벤트 리스너 목록을 저장 및 관리한다. (Subject)

UI가 필요한 윷놀이 게임의 특성 상, 주기적으로 모델들의 상태 등에 따라 뷰를 주기적으로 업데이트해야 한다. 이를 위해 옵저버 패턴을 사용하여, 말 이동, 턴 변경 등을 이벤트로 관리하고 이벤트 발생 시 자동으로 변경사항을 뷰에 반영하도록 설계하였다. 이를 통해 모델(Game)이 직접 뷰(GameView)를 호출하지 않고도 데이터 처리 및 전달을 수행할 수 있다.

또한 요구사항에 의하면 여러가지 UI 프레임워크로 UI를 개발해야 한다. 따라서 이벤트 기반으로 설계하여, 이벤트가 수신해서 처리하는 뷰를 바꿔 끼우는 방식으로 설계하였다. 이를 통해 UI 프레임워크에 독립적인 로직을 구성할 수 있다.

### 전략 패턴

윷 던지기와 보드 레이아웃 부분에서 전략 패턴을 사용하였다.

요구사항에 의하면 윷 던지기 방식이 랜덤과 지정, 두 가지 방식을 구현해야 한다.

따라서 기본적인 윷던지기 전략 인터페이스(YutThrowStrategy)를 만들고, 해당 전략 인터페이스를 랜덤 던지기와 지정 던지기 클래스에서 구현하도록 하였다.

또한, 보드 레이아웃의 경우, 사각형, 오각형, 육각형 보드를 사용할 수 있어야 한다.

따라서 기본적인 보드 레이아웃 전략 인터페이스(BoardLayoutStrategy)를 만들고, 각각의 보드 모양에 맞는 구체적인 보드 레이아웃 전략 클래스를 구현하여 3가지 모양의 보드를 구현하였다.

전략 패턴을 사용함으로써 새로운 윷던지기 방식이 추가되거나, 새로운 레이아웃이 추가되는 등 확장에 용이한 설계를 하였다.

실제로 보드 레이아웃의 경우, 사각형 보드 버전으로만 먼저 구현하였다. 추후 오각형, 육각형에 해당하는 새로운 보드 레이아웃 전략 클래스를 구현하여 최종적으로 다양한 보드를 사용할 수 있는 윷놀이 게임을 개발하였다.

## 주요 클래스 설명
### Controller

#### GameController

**역할**

이 프로그램에서 Game 모델과 GameView 사이에서 이벤트를 통해 중재하는 역할

뷰로부터 이벤트를 수신받아 모델에 상태 변경 등을 지시한다.

모델의 상태, 이벤트를 통해 뷰가 그려질 수 있도록 뷰에 데이터를 전달한다.

**기능**

- 뷰 초기화
    - 메소드: initializeGame
    - UI를 그리는 뷰를 초기화하고 설정하기 위한 다이얼로그를 띄워 게임설정을 진행할 수 있도록 한다.
- 게임 세팅
    - 메소드: setupGame
    - 사용자가 입력한 설정값으로 Game 클래스를 세팅하고, UI를 설정값에 맞게 세팅한다.
- 윷 던지기 가능 여부 관리
    - 메소드: handleYutThrow
    - 윷이나 모로 한번 더 던질 수 있거나, 아직 윷을 던지지 않은 경우만 윷을 던질 수 있도록 관리한다.
- 사용할 윷 선택 및 이동할 말 선택
    - 메소드: handleHorseSelection
    - 사용자가 특정 말을 클릭했을 때 호출된다.
    - 현재 사용 가능한 윷 결과 목록을 View에 요청해 선택하도록 하고, 유효한 선택이 있을 경우 game.moveHorse 메소드를 호출하여 말 이동을 처리한다.
- 게임 재시작
    - 메소드: handleRestartGame
    - (게임이 끝난 후 사용자가 재시작을 선택하는 경우) Game 클래스의 restart 메소드를 호출하여 게임 상태를 SET_UP 상태로 변경 및 새로운 게임을 시작할 수 있도록 한다.
- 게임 종료
    - 메소드: handleExitGame
    - (게임이 끝난 후 사용자가 종료를 선택하는 경우) 각 UI 프레임워크 종류에 맞춰 UI 종료 작업을 수행한다.


### Model

#### Game

**역할**

이벤트를 받아 전반적인 게임 관리 및 진행 역할

**주요 기능**

- 게임 상태 관리
    - GameState Enum을 이용하여, 게임의 상태를 SETUP, IN_PROGRESS, FINISHED 3가지 경우로 관리한다.
- 게임 실행
    - 메소드: setupGame
    - 최초 게임 설정 및 실행을 수행한다.
- 턴 관리
    - 메소드: nextTurn, getCurrentPlayer, getCurrentResults, canThrowAgain
    - 말 이동이 모두 끝나면 nextTurn 메소드가 실행된다. 현재 플레이어를 다음 플레이어로 업데이트하고, 윷결과 등 초기화하여 턴을 넘긴다.
    - 턴 관리를 위해 필요한 현재 플레이어 조회, 현재 윷 결과 목록 조회, 윷 던지기를 한번 더 할 수 있는 여부 조회 기능을 구현하였다.
- 말 이동
    - 메소드: hasMovableHorses, moveHorse, captureHorses, groupHorses
    - 사용자의 윷 결과를 바탕으로 이동 가능한 말이 있는지 체크한다.
    - 게임 상태와 말이 해당 플레이어의 것이 맞는지, 말의 완주 여부를 체크한다.
    - 말이 그룹 상태인지, 단일 상태에 인지에 따라 말이 이동할 위치를 계산 및 이동한다.
    - 해당 위치에 같은 팀의 말이나 상대 팀의 말이 있는지 체크하여, 그룹핑 또는 잡기를 수행한다.
        - captureHorses, groupHorses
    - 사용자가 선택한 말과 윷 결과로 말을 이동시키고, 사용한 윷결과는 저장된 리스트에서 제거한다.
    - 모든 이동이 끝나면 다음 플레이어에게 턴을 넘긴다.
- 윷던지기
    - 메소드: throwYut
    - 윷던지기 전략(랜덤 또는 지정)에 따라 Yut 클래스의 throwYut 메소드를 실행하여 윷던지기 수행 후 윷 결과를 저장한다.
    - 말이 없는 상태에서 빽도가 나오면 자동으로 턴을 넘긴다.
    - 윷, 모가 나오면 한번 더 던질 수 있도록 한다.
- 게임 종료 및 승자 판별
    - 메소드: endGame
    - 게임 상태를 FINISHED 상태로 초기화하고 승자를 결정 및 게임 종료 이벤트를 실행한다.
- 게임 재시작
    - 메소드: restart
    - 게임 상태를 SET_UP 상태로 초기화하여 게임을 다시 시작할 수 있도록 상태를 관리한다.

#### Board, Line, Path, Spot

**역할**

게임이 진행되는 판 상태 관리 및 이동 경로 시스템 제공

Board: 게임 판 상태 및  이동 경로 계산

Spot: 말이 서있을 수 있는 개별 칸

Path: 메인 루트, 대각선 지름길 등, 스팟으로 연결된 이동 경로

Line: Spot 간의 연결 정보를 시각적으로 표현하는 선 (UI 그리기 용도)

**기능**

**Board**

- spot, path, line, 도착지점(finishSpot) 데이터와 말들의 위치 데이터 저장
- 말 위치 업데이트
    - 메소드: updateHorsePosition
    - 기존 말들의 위치 리스트를 초기화하고, 현재 말들의 위치를 저장한다.
    - Game 클래스에서 실행
- 윷결과에 따라 이동 경로 계산
    - 메소드: calculateNextSpot, moveAlongPath, moveAlongMainPath, calculateFirstMove
    - 말의 출발 여부, 말의 완주 여부, 윷결과가 빽도인지 여부를 확인하고, 메인 경로와 지름길 경로 중 해당하는 곳 판별하여 말이 이동할 위치를 계산한다.

**Spot**

- 고유 Id, 시작점 여부, 종료점 여부, 꼭짓점 여부 데이터 저장
- 해당 스팟에서 이동 가능한 다음 스팟 데이터 저장 (메인 경로)
    - 메소드: addNextSpot
- 윷 결과에 따라 이동할 스팟 데이터 조회
    - 메소드: getNextSpot
    - 재귀적으로 각 윷 결과에 해당하는 위치의 스팟 데이터를 계산하여 제공한다.
- 해당 스팟에서 이동 가능한 경로 데이터 저장
    - 메소드: addNextPath
- 각 윷 결과에 따라 이동할 경로 데이터 제공
    - 메소드: getNextPath

**Path**

- 경로에 스팟 추가
    - 메소드: addSpot
- 이동 칸 수만큼 이동한 위치의 스팟 계산
    - 메소드: getSpotAfterMove

**Line**

- from, to 로 시작 스팟과 종료 스팟 데이터 저장

![ClassDiagramBoard](report_img/class_diagram_Board.png)

#### Player

**역할**

윷놀이 게임에 참여한 사용자로 게임 진행의 주체

말의 소유자

**기능**

- 플레이어 이름(번호), UI 내 표현 색상, 소유한 말 리스트 데이터 저장 및 관리
- 플레이어 객체 생성 시, 내부적으로 게임 시작 때 세팅한 말 갯수만큼 말 생성
- 승리 여부를 확인하기 위해 가진 말들이 모두 완주했는지 여부 확인
    - 메소드: isAllHorsesFinished
- 종료된 말의 갯수 조회
    - 메소드: getFinishedHorseCount

#### Horse, HorseGroup

**역할**

게임판 위를 이동하며 플레이어의 승패를 결정짓는 객체

각 플레이어에게 속해 있다.

HorseGroup은 같은 플레이어의 말끼리 업기(그룹핑)을 한 경우, 속한 말들의 이동 및 제거를 동시에 관리하기 위한 객체

**기능**

- 말 식별자, 소유 플레이어, 현재 위치, 완주 여부, 그룹핑 데이터 저장 및 관리
- 말 이동
    - 메소드: move
    - 말의 완주 여부, 그룹핑 여부 확인한다.
    - 단일 말과 그룹핑 말은 각자의 move 메소드로 이동 수행한다.
    - 말의 현재 위치를 목적지 스팟으로 업데이트하고, 목적지가 도착지점이라면 완주 여부를 true로 설정한다.
- (HorseGroup의 경우) 그룹핑된 말이 잡히는 경우를 위한 말의 그룹핑 해제 기능
    - 메소드: removeHorse
    - 그룹된 말들의 리스트를 초기화하고 모든 말의 그룹을 null로 설정한다.
- (HorseGroup의 경우) 그룹에 말 추가
    - 메소드: addHorse

#### Yut

**역할**

윷 던지기 결과를 생성한다.

랜덤 또는 지정 방식의 윷던지기 중 하나를 선택하여 수행할 수 있다.

**기능**

- 랜덤, 지정 중 윷 던지기 전략 설정
    - 메소드: setStrategy
- 윷 던지기
    - 메소드: throwYut
    - 설정되어 있는 윷 던지기 전략으로 윷 던지기를 수행하고 결과를 반환한다.

#### YutThrowStartegry

**역할**

랜덤 윷던지기와 지정 윷던지기 구현을 위해 추상화한 전략 인터페이스

**메소드**

throwYut

throwYut 메소드로 각 전략에 해당하는 알고리즘으로 윷던지기를 구현하도록 하였다.

![ClassDiagramYutThrow](report_img/class_diagram_YutThrow.png)

#### BoardLayoutStrategy

**역할**

사각형, 오각형, 육각형 보드 구현을 위해, Spot, Line, Path 등 게임판의 레이아웃 생성 전략을 추상화한 전략 인터페이스

**메소드**

createBoard

createBoard 메소드로 사각형, 오각형, 육각형 보드에 따라 각각의 UI를 그리기 전략 및 이동 전략에 맞게 보드를 생성하도록 구현하였다.

![ClassDiagramBoardLayout](report_img/class_diagram_BoardLayout.png)

#### GameEvent

**역할**

게임 내에서 발생하는 이벤트 객체

GameEventType Enum을 사용해 이벤트의 종류 데이터를 가진다. (type 필드)

플레이어, 윷 결과, 승자 등등 이벤트에 담을 수 있는 정보를 Map 형식으로 저장한다. (data 필드)

다양한 이벤트를 공통적인 구조로 관리할 수 있도록 설계하였다.

이벤트 종류는 다음과 같다.

GAME_SETUP: 게임 설정
YUT_THROW: 윷 던지기
HORSE_MOVE: 말 이동
CAPTURE: 말 잡기
GROUP: 말 업기
TURN_CHANGE: 턴 변경
GAME_OVER: 게임 종료

#### GameEventListenr

**역할**

이벤트를 수신하는 이벤트 리스너 인터페이스

onGameEvent 메소드를 통해 이벤트를 수신하여 처리한다.

GameView에서 이 인터페이스를 상속받고, 이 GameView 인터페이스는 JavaFXGameView, SwingGameView 등 각 UI 프레임워크의 GameView가 구현한다.

#### GameEventManager

**역할**

이벤트 리스너를 등록하고 이벤트를 전달하는 역할

**기능**

- 이벤트 리스너 등록
    - 메소드: addListener
- 등록된 이벤트 리스너에게 이벤트 전달
    - 메소드: fireEvent

### View

#### GameView

**역할**

다양한 UI 프레임워크에 대응하기 위해 설계된 View 인터페이스

**기능**

UI 초기화, 보드 업데이트, 플레이어 업데이트, 윷 결과 보여주기, 게임 결과 보여주기, 다이얼로그 띄우기, UI 종료 등 UI와 관련된 사용자와의 입출력 인터페이스를 정의하고 있다.

#### SwingGameView

**역할**

JavaSwing 프레임워크 기반으로 GameView 인터페이스를 구현한 클래스로, JavaSwing 기반 UI가 실행될 수 있도록 한다.

#### JavaFXGameView

**역할**

JavaFX 프레임워크 기반으로 GameView 인터페이스를 구현한 클래스로, JavaFX 기반 UI가 실행될 수 있도록 한다.

![ClassDiagramView](report_img/class_diagram_View.png)



## 개발 과정

### 1. Inception

먼저 첫 회의에서 요구사항 명세서를 분석하였다.

기본적인 윷놀이 규칙에 대해 이야기하며 고려해야 할 부분을 정리하였다. 이때 윷 한번 더 던지는 케이스, 지름길에서의 빽도 규칙 등 각자 상이하게 파악하고 있는 사소한 규칙들에 대해 논의하고 하나로 통일하였다.

### 2. Elaboration

![simple_class_diagram](report_img/simple_class_diagram.jpg)

분석한 요구사항을 바탕으로 유스케이스 모델을 만들며 필요한 기능을 정리하였다.

요구사항을 분석하여 보드 모양, UI 프레임워크 등 확장이 필요한 부분을 파악하고, 먼저 JavaSwing 프레임워크를 사용하여 사각형 보드로 윷놀이 게임을 구현하되, 보드 모양, UI 프레임워크를 확장할 수 있게 설계하였다.

이를 위해 MVC 구조로 Model, View, Controller로 구분해 필요한 클래스들을 파악하고, 간단한 버전의 클래스 다이어그램을 그리며 필요한 모델 객체들을 우선 정의하였다.

또한, 재사용성, 확장성, 로직과 UI의 관심사 분리를 위해 이벤트 기반으로 윷놀이 게임을 설계하였다.

### 3. Construction

**iteration1**

개발 초기에는 사각형 보드의 윷놀이 게임을 우선 개발하고, 추후 오각형, 육각형 보드를 개발하기로 하였다.

이때 전반적인 로직을 그대로 적용하며 오각형, 육각형 보드로 확장하기 위해, 전략 패턴과 팩토리 패턴을 사용하여 설계하였다.

이렇게 사각형 보드로 게임 시작, 게임 설정, 말 이동, 말 선택, 경로 계산, JavaSwing으로 뷰 그리기 등 기본적인 기능을 구현하였다.

사각형 보드 윷놀이 게임을 개발할 때, 요구사항 중 하나인 ‘여러 UI 프레임워크로 윷놀이 게임 구현’을 위해 MVC 구조와 이벤트 기반으로 설계하였다.

기본적인 기능을 구현한 후 테스트 코드와 실사용 테스트를 통해 버그 등을 수정하며 윷놀이 게임의 전반적인 알고리즘을 구현하였다.

**iteration2**

이후에는 사각형 보드로 구현했던 윷놀이 게임에 JavaSwing으로 보드 뷰를 구현하고, 오각형 보드와 육각형 보드의 레이아웃 전략도 구현하였다.

이를 위해 오각형, 육각형 보드에서의 지름길 이동 규칙 등을 논의하고 정리하였다.

오각형, 육각형 보드를 모두 구현한 후, 테스트를 통해 요구사항대로 올바르게 동작하는지 확인하였다.

또한 MVC 구조에 적합하게 구현되었는지 점검하며 리팩토링을 진행하였다.

**iteration3**

JavaSwing으로 구현했던 윷놀이 게임을 기반으로 JavaFX 프레임워크를 사용해 UI를 구현하였다.

JavaFX 프레임워크에 맞게 GameController와 GameViewFactory, YutGameApplication 클래스를  최소한으로 수정하여, JavaFX 프레임워크로도 윷놀이 게임을 구현하였다.

### 4. Transition

최종적으로 구현한 로직들을 테스트하기 위해 전체적인 테스트 코드를 작성하고, 실행로 프로그램을 실행해보며 프로그램이 버그없이 요구사항에 맞게 잘 동작하는지 확인하고 수정하였다.

또한, JUnit, JavaFX 등 이 프로그램을 실행하기 위해 필요한 패키지 설치 방법과 실행 방법을 README.md 파일에 정리하였다.

## UI를 교체했을 때 수정된 부분
**GameController.java**

해당 파일을  별도의 수정 없이 실행시켰을 때, “Not on FX application thread”라는 오류가 발생하였다. 해당 오류는 JavaFX 어플리케이션에서 UI 관련 작업을 JavaFX 스레드가 아닌 다른 스레드에서 시도할 때 발생한다. 따라서, JavaFX 사용 시, 백그라운드 스레드가 아닌 반드시 JavaFX의 UI 스레드에서 UI 관련 작업이 이루어짐을 보장해야 한다.

따라서, runOnUIThread 헬퍼 함수를 정의하였다. UI의 종류를 확인해서 Swing인 경우 그대로 실행하지만, JavaFX인 경우 현재 JavaFX Application 스레드에 있는지 검사한다. 만약 그렇다면 그대로 실행(action.run())하고, 아니라면 나중에 JavaFX UI 스레드에서 실행(Platform.runLater(action))한다.

컨트롤러의 모든 View 관련 작업은 위의 헬퍼 메소드를 사용하여 처리함으로써, 스레드 오류 없이 작동할 수 있도록 하였다.

**JavaFXGameView.java**

GameController 수정 사항에서 언급했던 UI 스레드 관련 문제를 해결하기 위해, stage와 관련된 작업은 전부 Platform.runLater() 안에서 처리하였다.

**GameViewFactory.java & YutGameApplication.java**

사용자로부터 uiType을 입력 받아 문자열로 전달하여, 어떤 UI(Swing 또는 JavaFX)를 사용할 지 결정할 수 있도록 하였다.

또한, `javafx.application.Platform` 을 사용하여 별도의 launcher 없이 하나의 메인 클래스 내에서 JavaFX를 실행 가능하도록 하였다.

**MVC 아키텍처**

UI를 교체하더라도 기본적인 Controller 구조와 Model의 수정은 필요하지 않았고, 메인 실행 클래스의 일부 내용 및 새로운 View 파일만 추가해주면 되었다. 따라서, MVC 패턴이 잘 지켜졌다고 할 수 있다.



# Sequence Diagram

![sequenceDiagram](report_img/sequence_diagram_윷놀이.jpg)

# 테스트 리포트
### 1. Test 환경

사용한 테스트 프레임워크: JUnit5

개발환경

- Java version 21.0.2
- Gradle version 8.14.1

### 2. Test 항목 및 시나리오

## BoardTest

| 테스트 메소드                         | 검증 포인트                                                            | JUnit Assertion 예시                                                         |
| ------------------------------------- | ----------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| **testGetSpotPosition**               | Spot ID에 매핑된 좌표(Point)를 올바르게 반환하는지                      | `assertEquals(new Point(10,0), board.getSpotPosition(s1));`                 |
| **testUpdateAndGetHorsesAtSpot**      | - Spot에 말 배치 후 조회<br>- 말 완주 처리 시 보드에서 제거되는지      | `assertEquals(1, atS1.size());`<br>`assertTrue(...isEmpty());`              |
| **testCalculateNextSpot_DO_and_BACKDO** | - DO 이동: 다음 칸으로<br>- BACKDO 이동: 이전 칸으로<br>- 시작 칸 BACKDO 시 null 반환 | `assertEquals(s2, nextFromS1);`<br>`assertNull(...);`                        |
| **testMoveAlongMainPath_multipleSteps** | - GAE(2) 이동 시 여러 칸 건너뛰어 도착<br>- 경로 끝 넘으면 도착 칸 반환 | `assertEquals(s2, twoSteps);`<br>`assertEquals(s2, overshoot);`             |

![BoardTest](report_img/1.png)

✅ BoardTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## GameEventManagerTest

| 테스트 메소드                                 | 검증 포인트                                               | JUnit Assertion 예시            |
| --------------------------------------------- | ---------------------------------------------------------- | ------------------------------- |
| **addListenerAndFireEvent_shouldCallListener** | 지정 이벤트 타입 리스너 등록 후 `fireEvent` 시 해당 리스너 호출 여부 | `assertTrue(called.get());`     |
| **duplicateListener_shouldBeRegisteredOnce**   | 동일 리스너 중복 등록 시 한 번만 호출되는지 (간접 검증)    | `assertTrue(called.get());`     |

![GameEventManagerTest](report_img/2.png)

✅ GameEventManagerTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## GameTest

| 테스트 메소드                                        | 검증 포인트                                                                                   | JUnit Assertion 예시                                                                                 |
| --------------------------------------------------- | ---------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| **setupGame_throwsOnTooFewPlayers**                 | 플레이어 수가 2 미만일 때 `IllegalArgumentException` 발생 여부                                  | `assertThrows(IllegalArgumentException.class, () -> …);`                                             |
| **testSetupGame_validParameters_initializesCorrectly** | 유효 파라미터로 `setupGame` 호출 시 내부 상태(플레이어 수, 상태, 보드, 윷결과 리스트)가 올바른지 확인 | `assertEquals(2, players.size());`<br>`assertEquals(IN_PROGRESS, game.getState());`                 |
| **testThrowYut_specificResult_addedToCurrentResults** | 지정 던지기 모드에서 결과가 `currentResults` 에 추가되고 `canThrowAgain()` 상태가 올바른지 검증   | `assertEquals(YutResult.DO, result);`<br>`assertFalse(game.canThrowAgain());`                       |
| **testThrowYut_MO_canThrowAgainTrue**               | MO(5) 결과 시 `canThrowAgain()`이 `true` 인지                                                  | `assertTrue(game.canThrowAgain());`                                                                  |
| **testMoveHorse_DO_movesHorseAlongMainPath**        | DO(1) 이동 시 말의 `currentSpot.id` 가 1 증가하고, 사용된 결과가 리스트에서 제거되는지         | `assertEquals(1, h.getCurrentSpot().getId());`<br>`assertTrue(game.getCurrentResults().isEmpty());` |
| **testCaptureEnemyHorse_returnsToStartAndCanThrowAgain** | 상대 말 잡기 시 상대 말이 시작 칸으로 복귀되고, 공격자가 한 번 더 던질 수 있는지 확인             | `assertTrue(defenderHorse.isAtStart());`<br>`assertTrue(game.canThrowAgain());`                     |

![GameTest](report_img/3.png)

✅ GameTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## HorseGroupTest

| 테스트 메소드                                      | 검증 포인트                                                             | JUnit Assertion 예시                                                            |
| ------------------------------------------------- | ------------------------------------------------------------------------ | -------------------------------------------------------------------------------- |
| **constructorAddsInitialHorse**                   | 생성자 호출 시 초기 말이 그룹에 포함되고, 말의 `group` 참조가 설정되는지 | `assertTrue(horses.contains(h1));`<br>`assertEquals(group, h1.getGroup());`       |
| **addHorseAddsNewHorse**                          | `addHorse(h2)` 호출 시 그룹 크기가 2가 되고, `h2.getGroup()` 이 `group` 인지 확인 | `assertEquals(2, horses.size());`<br>`assertEquals(group, h2.getGroup());`       |
| **addHorseNullOrDuplicateDoesNothing**            | `null` 또는 중복 말 추가 시 그룹 변화가 없는지                           | `assertEquals(1, group.getHorses().size());`                                     |
| **removeHorseRemovesAndClearsGroupWhenOneLeft**   | 두 마리 중 하나 제거 후 남은 한 마리까지 그룹 해제 및 `group` 참조가 `null` 이 되는지 | `assertTrue(group.getHorses().isEmpty());`<br>`assertNull(h2.getGroup());`       |
| **removeHorseWhenNotInGroupDoesNothing**          | 그룹에 없는 말 제거 시 아무 변화 없는지                                  | `assertEquals(1, group.getHorses().size());`                                     |
| **moveSetsAllHorsesToDestinationAndMarksFinished** | 일반/도착 칸으로 이동 시 모든 말의 `currentSpot` 과 `isFinished` 플래그 상태 검증 | `assertEquals(normalSpot, h1.getCurrentSpot());`<br>`assertTrue(h1.isFinished());` |
| **getHorsesReturnsUnmodifiableList**              | `getHorses()` 로 반환된 리스트가 수정 불가능한지                         | `assertThrows(UnsupportedOperationException.class, () -> list.add(h2));`         |

![HorseGroupTest](report_img/4.png)

✅ HorseGroupTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## HorseTest

| 테스트 메소드                             | 검증 포인트                                                                                 | JUnit Assertion 예시                                                            |
| ---------------------------------------- | -------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| **testInitialState**                     | - 초기 `id`, `owner`<br>- `currentSpot` null<br>- `isFinished`, `isInGroup` false           | `assertEquals(7, horse.getId());`<br>`assertNull(horse.getCurrentSpot());`     |
| **testMoveUpdatesSpot**                  | `move(spotA)` 호출 시<br>- `currentSpot` 업데이트<br>- `isFinished` false                   | `assertTrue(moved);`<br>`assertEquals(spotA, horse.getCurrentSpot());`         |
| **testMoveToFinishSpotSetsFinished**     | `move(spotFinish)` 호출 시<br>- `currentSpot` 업데이트<br>- `isFinished` true               | `assertTrue(horse.isFinished());`                                               |
| **testCannotMoveWhenAlreadyFinished**    | 이미 `finished` 상태일 때 `move()` false, `currentSpot` 변하지 않는지                       | `assertFalse(moved);`<br>`assertEquals(spotA, horse.getCurrentSpot());`         |
| **testCannotMoveWhenInGroup**            | 그룹에 속한 말일 때 `move()` false, `currentSpot` 변하지 않는지                            | `assertFalse(moved);`<br>`assertNull(horse.getCurrentSpot());`                  |
| **testToStringContainsIdAndOwnerName**   | `toString()` 결과에 `id` 와 `owner.name` 포함 여부                                         | `assertTrue(str.contains("id=7"));`<br>`assertTrue(str.contains("Tester"));`    |

![HorseTest](report_img/5.png)

✅ HorseTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## LineTest

| 테스트 메소드                                      | 검증 포인트                                                          | JUnit Assertion 예시                                                         |
| ------------------------------------------------- | --------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| **equals_sameFromTo_shouldBeEqual**               | 동일 `from`/`to` 로 생성한 두 객체가 `equals` 와 `hashCode` 동등      | `assertEquals(l1, l2);`<br>`assertEquals(l1.hashCode(), l2.hashCode());`     |
| **equals_differentFromOrTo_shouldNotBeEqual**     | `from`/`to` 순서가 다르거나 `to` 가 다를 때 `equals` false            | `assertNotEquals(l1, l2);`<br>`assertNotEquals(l1, l3);`                     |
| **equals_nullOrOtherType_shouldReturnFalse**      | `null` 또는 다른 타입과 비교 시 `equals` false                        | `assertNotEquals(l, null);`<br>`assertNotEquals(l, "string");`               |
| **getters_shouldReturnConstructorValues**         | `getFrom()`/`getTo()` 가 생성자 인자 객체를 그대로 반환               | `assertSame(a, l.getFrom());`<br>`assertSame(b, l.getTo());`                 |

![LineTest](report_img/6.png)

✅ LineTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## PathTest

| 테스트 메소드                               | 검증 포인트                                                      | JUnit Assertion 예시                                                             |
| ------------------------------------------ | ----------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| **testNameAndShortcutFlag**                | 생성자 인자 `name`, `isShortcut` 필드 반영 여부                 | `assertEquals("main", mainPath.getName());`<br>`assertTrue(shortcutPath.isShortcut());` |
| **testAddSpotAndPrevSpotLinking**          | `addSpot()` 후 `getSpots()` 순서, 각 Spot 의 `prevSpot` 연결     | `assertSame(s0, list.get(0));`<br>`assertSame(s0, s1.getPrevSpot());`            |
| **testGetLastSpotEmptyAndNonEmpty**        | 빈 경로 `getLastSpot()` null, Spot 추가 시 마지막 요소 반환      | `assertNull(empty.getLastSpot());`<br>`assertSame(s1, mainPath.getLastSpot());`  |
| **testGetSpotAfterMoveValid**              | 경로 내 n칸 이동 시 올바른 Spot 반환                             | `assertSame(s1, mainPath.getSpotAfterMove(s0,1));`<br>`assertSame(s2, mainPath.getSpotAfterMove(s0,2));` |
| **testGetSpotAfterMoveInvalid**            | 경로 벗어남, 존재하지 않는 Spot, 빈 경로 경우 null 반환         | `assertNull(mainPath.getSpotAfterMove(s0,5));`<br>`assertNull(empty.getSpotAfterMove(s0,1));` |
| **testGetSpotsUnmodifiable**               | `getSpots()` 로 반환된 리스트 수정 시 예외 발생                 | `assertThrows(UnsupportedOperationException.class, () -> spotsView.add(s1));`     |

![PathTest](report_img/7.png)

✅ PathTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## RandomYutThrowStrategyTest

| 테스트 메소드                                | 검증 포인트                                               | JUnit Assertion 예시                                                     |
| ------------------------------------------- | ---------------------------------------------------------- | ------------------------------------------------------------------------- |
| **testThrowYut_MO_whenZeroTrues**           | `nextBoolean()` 모두 false → `MO` 반환                     | `assertEquals(YutResult.MO, strategy.throwYut());`                     |
| **testThrowYut_DO_whenOneTrue**             | 정확히 1회 true → `DO` 반환                                | `assertEquals(YutResult.DO, strategy.throwYut());`                     |
| **testThrowYut_GAE_whenTwoTrues**           | 정확히 2회 true → `GAE` 반환                               | `assertEquals(YutResult.GAE, strategy.throwYut());`                    |
| **testThrowYut_GEOL_whenThreeTrues**        | 정확히 3회 true → `GEOL` 반환                              | `assertEquals(YutResult.GEOL, strategy.throwYut());`                   |
| **testThrowYut_YUT_whenFourTrues**          | 4회 모두 true → `YUT` 반환                                 | `assertEquals(YutResult.YUT, strategy.throwYut());`                    |
| **testThrowYut_MultipleRunsCoverAllCases**  | 랜덤 반복 실행 시 null 아니며 가능한 결과만 반환           | `assertTrue(Set.of(YutResult.values()).contains(r));`                  |

![RandomYutThrowStrategyTest](report_img/8.png)

✅ RandomYutThrowStrategyTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음

## SpotTest

| 테스트 메소드                                    | 검증 포인트                                                          | JUnit Assertion 예시                                                         |
| ----------------------------------------------- | --------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| **testIdAndFlags**                              | `id`, `isStart`, `isCorner`, `isFinish` 플래그 초기값 올바른지       | `assertEquals(0, s0.getId());`<br>`assertTrue(s0.isStart());`               |
| **testAddNextSpotAndPrevSpot**                  | `addNextSpot(YUT/GAE 등)` 시 `getNextSpot()`, `prevSpot` 설정 여부 | `assertSame(s1, s0.getNextSpot(YutResult.DO));`<br>`assertSame(s0, s1.getPrevSpot());` |
| **testGetNextSpotChaining_DO_GAE_GEOL**         | 체이닝된 DO/GAE/GEOL 호출 시 올바른 Spot 반환, 오버슈트 시 null   | `assertSame(s2, s0.getNextSpot(YutResult.GAE));`<br>`assertNull(s0.getNextSpot(YutResult.YUT));` |
| **testBackdo**                                  | `BACKDO` 호출 시 `prevSpot` 반환, 시작 칸 시 null 반환             | `assertSame(s1, s2.getNextSpot(YutResult.BACKDO));`<br>`assertNull(s0.getNextSpot(YutResult.BACKDO));` |
| **testAddAndGetNextPath**                       | `addNextPath()` 후 `hasPath()`, `getNextPath()` 동작 여부         | `assertTrue(s0.hasPath(YutResult.YUT));`<br>`assertSame(shortcut, s0.getNextPath(YutResult.YUT));` |
| **testGetNextSpotWithOnlyPathEntry**            | Path 엔트리만 있을 때 기본 로직(없으면 null) 동작 확인            | `assertNull(s0.getNextSpot(YutResult.YUT));`                                |
| **testToString**                                | `toString()` 결과가 `"Spot{id=<id>}"` 형태인지                   | `assertEquals("Spot{id=0}", s0.toString());`                               |

![SpotTest](report_img/9.png)

✅ SpotTest의 빌드와 테스트(위에 적힌 테스트 메소드 전부)를 모두 통과한 결과인 BUILD SUCCESSFUL을 확인할 수 있음


# Github 프로젝트 리포트
## GitHub 사용 & 개발 워크플로우

### 1. Repository Structure

- `main` 브랜치
    - README.md
- `dev` 브랜치
    - 일일 통합 브랜치 (최종 코드가 담긴 Branch)
    - 기능이 완성될 때마다 Pull Request로 머지
- `feat/<숫자(이슈번호)>` 브랜치
    - 새로운 기능이나 개선 작업마다 생성
    - 예: `feat/35` 등

### 2. Issues (22개)

#1 Write README.md

#2 Make base code

#4 Horses, Player → branch `feat/4` -m ‘replaced Player and Horse’

#6 Yut, Game, Board

#7 add hasThrownYut → branch `feat/7` -m ‘add hasThrownYut’

#10 🐛 말이 업혔을 때 잡았다고 뜸

#12 🐛 Player이름이 알파벳G으로 바뀌어짐

#14 각 꼭짓점, 중앙점에서 지름길로 이동하지 않고 있음 → branch `feat/14` -m ‘도착 안되던거 고침’

#15 도착점에서 상대편 말이 잡힘 

#18 말이 하나도 없을 때 빽도가 나오면 윷을 못던짐

#20 대각선 빽도 

#21 빽도 오류 

#24 오각형, 육각형 view → branch `feat/24` -m ‘오각형, 육각형 view’

#26 오각형, 육각형 Path → branch `feat/26` -m ‘육각형, 오각형 Path 추가’

#28 대각선 빽도 이동

#29 모-빽도 하면 턴 넘어감 수정 ( && currentResult.isEmpty() 추가 )

#30 말이 하나도 없을 때 빽도 나오면 윷을 못 던짐 수정 ( canThrowAgain = true; 추가 )

#33 MVC 패턴 

#34 Strategy 함수 분리

#35 Create Test Code

#36 JavaFX Game View

#39 🐛 사각형 보드 대각선 이동 오류

### 3. Branches

### ✅ “gradle-test-env” branch

- Gradle을 활용하여 JUnit 테스트 코드를 돌림.
- Gradle을 구동하는데 필요한 모든 파일 및 폴더를 담은 브랜치.
- JavaFXGameView.java의 `ChoiceDialog<YutResult> dialog = new ChoiceDialog<>(options.getFirst(), options);` 를 `ChoiceDialog<YutResult> dialog = new ChoiceDialog<>(options.get(0), options);` 으로 바꿈. (Gradle 구동 시 오류가 발생하여 이 부분만 수정함.)

# GitHub Link
[GitHub - betterrchae/sw01-12](https://github.com/betterrchae/sw01-12.git)